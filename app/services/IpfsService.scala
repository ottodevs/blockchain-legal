package services

import java.io.File

import org.ipfs.api.{IPFS, Multihash, NamedStreamable}

import scala.util.{Failure, Success, Try}

/**
  * Created by davidroon on 31.07.16.
  * This code is released under Apache 2 license
  */
class IpfsService(val url:String) {
  def add(file: File) = ipfs.map(ipfsClient => {
    val ipfsFile = new NamedStreamable.FileWrapper(file)
    val addResult = ipfsClient.add(ipfsFile)
    addResult.hash.toBase58
  })

  private var ipfsConnection:Try[IPFS] = initIpfs
  private def initIpfs = {
    Try(new IPFS(url))
  }

  def error:Option[Throwable] = ipfsConnection match {
    case Success(_) => None
    case Failure(ex) => Some(ex)
  }

  def ipfs:Option[IPFS] = ipfsConnection match {
    case Success(ipfs) => Some(ipfs)
    case Failure(ex) =>
      ipfsConnection = initIpfs
      None
  }
  def cat(filePointer: Multihash):Array[Byte] = {
    ipfs.map(_.cat(filePointer)).getOrElse(Array[Byte]())
  }
}
