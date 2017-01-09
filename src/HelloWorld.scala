import java.util.regex.Pattern

object HelloWorld {
  def main(args: Array[String]): Unit = {
    println("Hello, world!")

    var sss = """
    this is one
    multi-line
    string  value"""
    println(sss)
    println(Pattern.compile("^\\s+", Pattern.MULTILINE).matcher(sss).replaceAll(""))
  }
}