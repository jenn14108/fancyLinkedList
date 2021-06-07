package fancyLinkedList;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Transaction {

	private String buyer;
	private String seller;
	private String hash;
	private String value;

}
