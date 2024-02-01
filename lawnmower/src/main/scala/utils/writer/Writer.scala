package utils.writer

import master.Master

object Writer {
  def writeMaster(toWrite: Master):Unit =
    val path = os.Path

    os.write.over(os.pwd / "output.txt", toWrite.toString)
}
