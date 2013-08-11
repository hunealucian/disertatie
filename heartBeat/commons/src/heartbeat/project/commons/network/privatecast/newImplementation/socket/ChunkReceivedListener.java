package heartbeat.project.commons.network.privatecast.newImplementation.socket;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public interface ChunkReceivedListener {
	public void onDataArrives(byte[] bytes, int n, int len);
}
