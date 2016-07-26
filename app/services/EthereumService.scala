package services

import javax.inject.{Inject, Singleton}

import org.adridadou.ethereum.{EthAddress, EthereumFacade}
import providers.BlockchainLegalConfig

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by davidroon on 24.07.16.
  * This code is released under Apache 2 license
  */
@Singleton
class EthereumService @Inject() (ethereum:EthereumFacade, config:BlockchainLegalConfig) {
  /*

  private val contract = Future {
    ethereum.createContractProxy(config.legalContractManagerConfig.code, "legalContractManager", config.legalContractManagerConfig.address,classOf[LegalContractManager])
  }
  */
  def getCurrentBlockNumber:Long = ethereum.getCurrentBlockNumber
  def isSyncDone:Boolean = ethereum.isSyncDone
  //def getContract:Future[LegalContractManager] = contract
}


trait LegalContractManager {
  /*
		Get the source of the package. Right now this is an IPFS hash but it should be possible to have any way of retrieving the source (HTTP, FTP etc ...)

		params:
		- Namespace: The namespace of the package you are looking for
		- name: the name of the legal documents
		- version: the version of the legal documents
	*/
  def getSource(namespace:String, name:String, version:String):String

  /*
    Register a new package.

    params:
    - Namespace: The namespace of the package you are looking for
    - name: the name of the legal documents
    - version: the version of the legal documents
  */
  def register(namespace:String, name:String, version:String, ipfs:String):Unit

  /*
    Create a new namespace and makes the tx.origin the owner of this namespace
  */
  def createNamespace(namespace:String, owner:EthAddress):Unit

  /*
    Passes the ownership of a namespace to someone else
  */
  def changeOwner(namespace:String, newOwner:EthAddress):Unit

  def canWrite(namespace:String, user:EthAddress):Boolean
}