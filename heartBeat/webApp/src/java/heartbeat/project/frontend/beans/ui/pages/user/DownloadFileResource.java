package heartbeat.project.frontend.beans.ui.pages.user;

import com.icesoft.faces.context.Resource;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 9/1/13
 */
public class DownloadFileResource implements Resource, Serializable {

    private String resourceName;
    private InputStream inputStream;

    @Override
    public String calculateDigest() {
        return null;
    }

    @Override
    public InputStream open() throws IOException {
        if (inputStream == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();

    //            InputStream stream = ec.getResourceAsStream(OutputResourceBean.RESOURCE_PATH + resourceName);
//            byte[] byteArray = OutputResourceBean.toByteArray(stream);

//            inputStream = new ByteArrayInputStream(byteArray);
        } else {
            inputStream.reset();
        }
        return inputStream;
    }

    @Override
    public Date lastModified() {
        return null;
    }

    @Override
    public void withOptions(Options options) throws IOException {

    }
}
