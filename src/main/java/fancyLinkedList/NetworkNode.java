package fancyLinkedList;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Used for testing purposes. Simulates a node connected over the public network
 * that validates and adds a new block to the chain. This does not encompass the
 * consensus mechanism.
 */
@Getter
@NoArgsConstructor
public class NetworkNode {

	private List<Transaction> transactions = new ArrayList<Transaction>();

	private BlockChain blockChain;

	// for serializing the list of transactions kept in the block to JSON
	private Gson parser = new Gson();

	public NetworkNode(BlockChain blockChain) {
		this.blockChain = blockChain;
	}

	public void mineBlock(Transaction transaction) {
		transactions.add(transaction);
		// create a new block once the transactions-per-block-limit is met
		if (transactions.size() == this.blockChain.getBlockSize()) {
			createBlockAndAddToChain();
		}
	}

	private void createBlockAndAddToChain() {
		Block block = blockChain.newBlock();
		// set the previous hash with the hash of the current block
		block.setPrevHash(blockChain.getLastBlockHash());
		// set the hash of this new block after completing POW
		block.setHash(doProofOfWork(block));
		blockChain.validateAndAddBlock(block);
		// empty transaction list
		transactions = new ArrayList<Transaction>();
	}

	private String doProofOfWork(Block block) {

		String nonceKey = block.getNonce();
		long nonce = 0;
		boolean nonceFound = false;
		String nonceHash = "";

		String serializedData = parser.toJson(transactions);
		String message = block.getTimestamp() + serializedData + block.getPrevHash();

		while (!nonceFound) {
			nonceHash = Hashing.sha256().hashString(message + nonce, StandardCharsets.UTF_8).toString();
			nonceFound = nonceHash.substring(0, nonceKey.length()).equals(nonceKey);
			nonce++;

		}

		return nonceHash;
	}

}
