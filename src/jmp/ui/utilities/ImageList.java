package jmp.ui.utilities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ImageList
{
	private List<BufferedImage> images;
	
	public ImageList()
	{
		this.images = new ArrayList<BufferedImage>();
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(BufferedImage arg0) {
		return images.add(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public void add(int arg0, BufferedImage arg1) {
		images.add(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends BufferedImage> arg0) {
		return images.addAll(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int arg0, Collection<? extends BufferedImage> arg1) {
		return images.addAll(arg0, arg1);
	}

	/**
	 * 
	 * @see java.util.List#clear()
	 */
	public void clear() {
		images.clear();
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.List#get(int)
	 */
	public BufferedImage get(int arg0) {
		return images.get(arg0);
	}

	/**
	 * @return
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {
		return images.isEmpty();
	}

	/**
	 * @return
	 * @see java.util.List#iterator()
	 */
	public Iterator<BufferedImage> iterator() {
		return images.iterator();
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.List#remove(int)
	 */
	public BufferedImage remove(int arg0) {
		return images.remove(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object arg0) {
		return images.remove(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> arg0) {
		return images.removeAll(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public BufferedImage set(int arg0, BufferedImage arg1) {
		return images.set(arg0, arg1);
	}

	/**
	 * @return
	 * @see java.util.List#size()
	 */
	public int size() {
		return images.size();
	}

	
}
