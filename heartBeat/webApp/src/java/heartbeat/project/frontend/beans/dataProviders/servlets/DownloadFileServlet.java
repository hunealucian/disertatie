package heartbeat.project.frontend.beans.dataProviders.servlets;

import heartbeat.project.commons.model.socketmsg.ChainInfo;
import heartbeat.project.commons.model.socketmsg.FileInfo;
import heartbeat.project.commons.model.socketmsg.NodeInfo;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.factory.send.SendData;
import heartbeat.project.commons.network.privatecast.factory.socket.ChunkReceivedListener;
import heartbeat.project.commons.network.privatecast.factory.socket.receive.stream.StreamReader;
import heartbeat.project.frontend.beans.dataProviders.clusterServices.ClusterService;
import heartbeat.project.frontend.beans.session.SessionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 9/2/13
 */
@Component("downloadFile")
public class DownloadFileServlet implements HttpRequestHandler {



    private static final int BUFSIZE = 8192;
    private String filePath;

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String filePath = (String) request.getParameter("filePath");
        String redirectTo = (String) request.getParameter("redirectTo");


        try {
            FileInfo fileInfo = new FileInfo(filePath.substring(filePath.lastIndexOf("/") + 1), filePath);

            SendData<FileInfo> sender = new SendData<FileInfo>(ClusterService.managerIp, ClusterService.managerPort, HeaderMessage.GIVE_FILE_NODE_CONNECTION, fileInfo);
            sender.send();

            StreamReader<NodeInfo> reader = new StreamReader<>(sender.getDataSocket());

            reader.fetch();

            reader.closeConnection();
            sender.closeConnection();
            HeaderMessage message = reader.getHeaderMessage();
            if (message == HeaderMessage.OK) {
                NodeInfo nodeInfo = reader.getMessageInfo();

                SendData<FileInfo> sendFileData = new SendData<FileInfo>(nodeInfo.getNode().getIpAddr(), nodeInfo.getNode().getReceiveMessagesPort(), HeaderMessage.SEND_FILE, fileInfo);
                sendFileData.send();

                StreamReader<FileInfo> receiveFileData = new StreamReader<>(sendFileData.getDataSocket());
                receiveFileData.fetch();

                if( receiveFileData.getHeaderMessage() == HeaderMessage.OK ){

                    FileInfo fileInfoReceived = receiveFileData.getMessageInfo();


                    final ServletOutputStream outStream = response.getOutputStream();

                    // sets response content type
                    response.setContentType("application/octet-stream");
                    response.setContentLength((int) fileInfoReceived.getSize());

                    // sets HTTP header
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileInfoReceived.getName() + "\"");

                    receiveFileData.fetchFile(new ChunkReceivedListener() {
                        @Override
                        public void onDataArrives(byte[] bytes, int n, int len) {
                            try {
                                outStream.write(bytes, n, len);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    receiveFileData.closeConnection();

                    outStream.close();
                }

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        response.sendRedirect( redirectTo);
    }
}
