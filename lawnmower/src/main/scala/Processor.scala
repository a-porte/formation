import master.Master
import reader.Reader
import utils.writer.Writer

@main def process(fileName: String) =
  Reader.readFromResources(fileName) match
    case None => throw Error("No lawn nor mowers can me read")
    case Some((lawn, mowers)) =>
      val playedMaster = Master(lawn, mowers).play
      Writer.writeMaster(playedMaster)
