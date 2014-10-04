package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;

public class Node<T>
{
	private Node<T> aParent;
	private ArrayList<Node<T>> aChildren = new ArrayList<Node<T>>();
	private T aElement;
	
	public Node(T pElement, Node<T> pParent)
	{
		aParent = pParent;
		aElement = pElement;
	}

	public Node(T pElement)
	{
		aElement = pElement;
		aParent = null;
	}
	
	public void add(Node<T> pChild)
	{
		aChildren.add(pChild);
	}
	
	public T get()
	{
		return aElement;
	}
	public ArrayList<Node<T>> getChildren()
	{
		return aChildren;
	}
}