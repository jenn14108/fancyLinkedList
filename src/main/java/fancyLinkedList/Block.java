package fancyLinkedList;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Block {
	// TODO: figure out if keeping track of parent block is necessary
	private Block parentBlock;
	// TODO: make this a generic list so it can hold any data type
	private List<Transaction> transactions = new ArrayList<>();
	// used as block origination verification
	private long timestamp;
	// blocks are linked only when the hash of the previous
	// block matches the hash of the current block
	private String prevHash;
	private String nonce = "0000"; // arbitrary number used in cryptography
	private String hash;
	// for serializing the list of transactions kept in the block to JSON
	private Gson parser = new Gson();
	
	public Block() {
		this.timestamp = System.currentTimeMillis();
	}

	/**
	 * Compute the hash of this particular block
	 */
	public String computeBlockHash() {
		return Hashing.sha256().hashString(parentBlock + Long.toString(timestamp) + nonce + parser.toJson(transactions),
				StandardCharsets.UTF_8).toString();
	}

	public boolean isValidHash() {
		return this.hash.equals(prevHash);
	}

	/**
	 * Adds a new transaction to the block and recomputes the hash of the block
	 */
	public Block add(Transaction txn) {
		transactions.add(txn);
		computeBlockHash();
		return this;
	}

}
