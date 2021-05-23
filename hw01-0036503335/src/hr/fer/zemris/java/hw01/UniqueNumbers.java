package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * This class creates an empty integer binary tree and adds
 * integers to it from the standard input (default keyboard) 
 * until the keyword "kraj" is entered.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class UniqueNumbers {
	
	static class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;
	}

	/**
	 * The method from which the program starts. It creates an empty binary tree
	 * and inserts integers into it from the standard input until the keyword
	 * "kraj" is entered. When the keyword "kraj" is entered the binary tree
	 * is printed in an ascending and descending order.
	 * 
	 * @param args command line arguments. Not used in this program.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TreeNode head = null;
		
		
		while (true) {
			System.out.print("Enter a number > ");
			if(sc.hasNext()) {
				String inputString = sc.next();
				if (inputString.equals("kraj")) {
					break;
				}
				int value;
				try {
					value = Integer.parseInt(inputString);
				} catch (NumberFormatException ex) {
					System.out.println(inputString + " is not a whole number.");
					continue;
				}
				if (containsValue(head, value)) {
					System.out.println("Tree already contains number. Skipping.");
				} else {
					head = addNode(head, value);
					System.out.println("Number added.");
				}
			}
		}
		
		
		System.out.println("Increasing order: " + printTreeWalk(head, true).trim());
		System.out.println("Decreasing order: " + printTreeWalk(head, false).trim());
		
		sc.close();
	}
	
	/**
	 * Adds an integer to the binary tree.
	 * 
	 * @param head the root of the binary tree.
	 * @param value the value to be inserted.
	 * @return the root of the binary tree.
	 */
	public static TreeNode addNode(TreeNode head, int value) {
		TreeNode node = new TreeNode();
		node.value = value;
		if (head == null) {
			head = node;
			return head;
		}
		TreeNode current = head;
		while (true) {
			if (current.value == value) {
				break;
			} else if(current.value > value) {
				if (current.left == null) {
					current.left = node;
					return head;
				} else {
					current = current.left;
				}
			} else {
				if (current.right == null) {
					current.right = node;
					return head;
				} else {
					current = current.right;
				}
			}
		}
		return head;
	}
	
	/**
	 * Checks if the binary tree contains the given value.
	 * 
	 * @param head the root of the binary tree.
	 * @param value the value to be checked.
	 * @return true if the tree contains the value, else false.
	 */
	public static boolean containsValue(TreeNode head, int value) {
		if (head == null) {
			return false;
		}
		TreeNode current = head;
		while (true) {
			if (current.value == value) {
				return true;
			} else if(current.value > value) {
				if (current.left == null) {
					return false;
				} else {
					current = current.left;
				}
			} else {
				if (current.right == null) {
					return false;
				} else {
					current = current.right;
				}
			}
		}
	}
	
	/**
	 * Returns the number of elements in the given tree.
	 * 
	 * @param head the root of the binary tree.
	 * @return number of elements in the given binary tree.
	 */
	public static int treeSize(TreeNode head) {
		if (head == null) {
			return 0;
		}
		int counter = 1;
		counter += treeSize(head.left);
		counter += treeSize(head.right);
		return counter;
	}
	
	/**
	 * Returns a string representation of the binary tree in 
	 * an ascending or descending ordering.
	 * 
	 * @param head the root of the binary tree.
	 * @param ascending a flag which determines the ordering 
	 *                  of the string representation. 
	 * @return the string representation of the binary tree.
	 */
	public static String printTreeWalk(TreeNode head, boolean ascending) {
		if (head == null) {
			return "";
		}
		String numbers = "";
		if (ascending) {
			numbers += printTreeWalk(head.left, ascending);
			numbers += Integer.toString(head.value) + " ";
			numbers += printTreeWalk(head.right, ascending);
		} else {
			numbers += printTreeWalk(head.right, ascending);
			numbers += Integer.toString(head.value) + " ";
			numbers += printTreeWalk(head.left, ascending);
		}
		return numbers;
	}

}
