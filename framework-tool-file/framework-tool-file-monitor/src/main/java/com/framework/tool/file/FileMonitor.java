package com.framework.tool.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Set;

public class FileMonitor {
    public static void main(String[] args) {
        final Path path = Paths.get("D://Monitor");
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            //给path路径加上文件观察服务
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
            RandomAccessFile file = new RandomAccessFile(path + "/text.txt", "rw");
            long length = file.length();
            System.out.println(length);
            while (true) {
                final WatchKey key = watchService.take();
                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                    final WatchEvent.Kind<?> kind = watchEvent.kind();
                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }
                    //创建事件
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        System.out.println("[新建]");
                    }
                    //修改事件
                    if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        Object context = watchEvent.context();
                        System.out.println(context);
                        file = new RandomAccessFile(path +"/"+context.toString(), "rw");
                        file.seek(length);//移动文件指针位置
                        byte[] buff = new byte[1024];
                        int hasRead = 0;
                        StringBuilder sb = new StringBuilder();
                        while ((hasRead = file.read(buff)) > 0) {

                            sb.append(new String(buff, 0, hasRead));
                        }

                        System.out.println(sb.toString());
                        length= file.length();

                        System.out.println("[修改]");
                    }
                    //删除事件
                    if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                        System.out.println("[删除]");
                    }
                    // get the filename for the event
                    final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
                    final Path filename = watchEventPath.context();
                    // print it out
                    System.out.println(kind + " -> " + filename);
                }
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException | InterruptedException ex) {
            System.err.println(ex);
        }
    }
}
