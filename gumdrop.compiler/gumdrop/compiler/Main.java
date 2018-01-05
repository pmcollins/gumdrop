package gumdrop.compiler;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    String dir = args.length == 1 ? args[0] : ".";
    FileSystem fs = FileSystems.getDefault();
    Path path = fs.getPath(dir);
    FileVisitor visitor = new FileVisitor();
    Files.walkFileTree(path, visitor);
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    System.out.println("compiler = [" + compiler + "]");
    StandardJavaFileManager fm = compiler.getStandardFileManager(null, null, null);
    Iterable<? extends JavaFileObject> javaFileObjects = fm.getJavaFileObjectsFromPaths(visitor.getPaths());
    System.out.println("javaFileObjects = [" + javaFileObjects + "]");
    //        compiler.getTask(null, fileManager, null, null, null, compilationUnits2).call();
    JavaCompiler.CompilationTask task = compiler.getTask(null, fm, null, null, null, javaFileObjects);
    task.call();
  }
}

class FileVisitor extends SimpleFileVisitor<Path> {
  private final List<Path> paths = new ArrayList<>();
  @Override
  public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
    if (path.toString().endsWith(".java")) {
      paths.add(path);
    }
    return super.visitFile(path, attrs);
  }
  List<Path> getPaths() {
    return paths;
  }
}
