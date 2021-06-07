package fancyLinkedList;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
//TODO: Think this through: shouldn't need a blockchain class. A block class should be sufficient in representing the whole chain...
public class BlockChain {
	// arbitrary maximum block size
	private static final int BLOCK_SIZE = 2;
	private List<Block> chain = new ArrayList<Block>();

	public BlockChain() {
		// create the genesis block
		// chain.add(newBlock());
	}

	public Block newBlock() {
		return new Block();
	}

	public void validateAndAddBlock(Block block) {
		// compare current block hash with hash of previous block
		Block currentBlock = block;

		for (int blockIndex = chain.size() - 1; blockIndex >= 0; blockIndex--) {
			Block comparerBlock = chain.get(blockIndex);
			if (comparerBlock.getHash().equals(currentBlock.getPrevHash())) {
				currentBlock = comparerBlock;
			} else {

				throw new RuntimeException("The block being added is Invalid");
			}

		}

		this.chain.add(block);
	}

	public String getLastBlockHash() {
		if (this.chain.size() == 0) {
			return "RootHash";
		}
		return getLastBlock().getHash();
	}

	public int getBlockSize() {
		return BLOCK_SIZE;
	}

	private Block getLastBlock() {
		if (this.chain.size() > 0) {
			return this.chain.get(this.chain.size() - 1);
		} else {
			throw new RuntimeException("There are no blocks in the chain...");
		}
	}

}
