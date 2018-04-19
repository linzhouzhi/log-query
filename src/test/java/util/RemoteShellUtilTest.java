package util;

import com.lzz.util.RemoteShellUtil;
import org.junit.Test;

/**
 * Created by lzz on 2018/4/19.
 */
public class RemoteShellUtilTest {
    @Test
    public void testfileList(){
        RemoteShellUtil remoteShellUtil = new RemoteShellUtil( "192.168.31.147", "lzz", "363216" );
        String res = remoteShellUtil.execRemote("ls -l /");
        System.out.println( res );
    }
}
