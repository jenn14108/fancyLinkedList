package fancyLinkedList;

public class Main {
	
	public static void main(String[] args) {
		
		
		NetworkNode networkNode = new NetworkNode(new BlockChain());
		Transaction t = new Transaction("XJS:LJF242","JSQOD134", "8743b52063cd84097a65d1633f5c74f5","10201");
		networkNode.mineBlock(t);
		Transaction t2 = new Transaction("XJS:LJF242","JSQOD134", "8743b52063cdkjgkjgkhk4f5","10201");
		networkNode.mineBlock(t2);
		System.out.println(networkNode.getBlockChain().getBlockSize());
		
	}

}
