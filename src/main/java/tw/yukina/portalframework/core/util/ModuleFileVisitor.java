package tw.yukina.portalframework.core.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.ArrayList;

import static java.nio.file.FileVisitResult.CONTINUE;

public class ModuleFileVisitor extends SimpleFileVisitor<Path> {
    private List<Path> pathArrayList = new ArrayList<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if(file.getFileName().toString().endsWith(".jar")){
            pathArrayList.add(file);
        }
        return CONTINUE;
    }


    public List<Path> getPathArrayList() {
        return pathArrayList;
    }
}
