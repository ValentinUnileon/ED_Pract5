package ule.edi.tree;

 
import java.util.Collection;
import java.util.Iterator; 
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;





public class BinarySearchTreeTests {

   
	/*
	* 10
	* |  5
	* |  |  2
	* |  |  |	∅
	* |  |  |	∅
	* |  |	 ∅
	* |  20
	* |  |  15
	* |  |  |	∅
	* |  |  | 	∅
	* |  |  30
	* |  |  |  	∅
	* |  |  |  	∅
    */	
	private BinarySearchTreeImpl<Integer> ejemplo = null;
	
	
	/*
	* 10
	* |  5
	* |  |  2
	* |  |  |  	∅
	* |  |  |  	∅
	* |  | 	 ∅
	* |  20
	* |  |  15
	* |  |  |  12
	* |  |  |  |  	∅
	* |  |  |  |  	∅
	* |  | 	 ∅
  */
	private BinarySearchTreeImpl<Integer> other=null;
	
	@Before
	public void setupBSTs() {
		
			
		ejemplo = new BinarySearchTreeImpl<Integer>();
		ejemplo.insert(10, 20, 5, 2, 15, 30);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}", ejemplo.toString() );
		
		
		other =new BinarySearchTreeImpl<Integer>();
		other.insert(10, 20, 5, 2, 15, 12);
		Assert.assertEquals(other.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20, {15, {12, ∅, ∅}, ∅}, ∅}}");
		
	 }
	
	@Test
	public void testInsertColection() {
		
		BinarySearchTreeImpl<Integer> nuevo =new BinarySearchTreeImpl<Integer>();
		
		Collection<Integer> coleccion = new LinkedList<Integer>();
		
		coleccion.add(20);
		coleccion.add(20);
		coleccion.add(30);
	
		nuevo.insert(coleccion);
		
		
		Assert.assertEquals(nuevo.toString(), "{20(2), ∅, {30, ∅, ∅}}");

	}
	
	
	@Test
	public void testInsert() {
		
		BinarySearchTreeImpl<Integer> nuevo =new BinarySearchTreeImpl<Integer>();
		nuevo.insert(20); 
		nuevo.insert(20); 
		nuevo.insert(30);
		Assert.assertEquals(nuevo.toString(), "{20(2), ∅, {30, ∅, ∅}}");

	}
	
	
	@Test
	public void testContains() {
		
		BinarySearchTreeImpl<Integer> nuevo =new BinarySearchTreeImpl<Integer>();
		nuevo.insert(20); 
		nuevo.insert(20); 
		nuevo.insert(30);
		nuevo.insert(10);
		Assert.assertEquals(nuevo.contains(30), true);
		Assert.assertEquals(nuevo.contains(20), true);
		Assert.assertEquals(nuevo.contains(10), true);
		Assert.assertEquals(nuevo.contains(15), false);

	}
	
	
	
	
	@Test
	public void testRemoveCountMayor1() {
		ejemplo.insert(20);
		ejemplo.insert(20);
		Assert.assertEquals(ejemplo.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20(3), {15, ∅, ∅}, {30, ∅, ∅}}}");
		ejemplo.remove(20);
	    Assert.assertEquals(ejemplo.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20(2), {15, ∅, ∅}, {30, ∅, ∅}}}");
	}
	
	@Test
	public void testRemoveCountMayor1HastaVaciar() {
		
		ejemplo.insert(20);
		ejemplo.insert(20);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20(3), {15, ∅, ∅}, {30, ∅, ∅}}}",ejemplo.toString());
		ejemplo.remove(20);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20(2), {15, ∅, ∅}, {30, ∅, ∅}}}",ejemplo.toString());
		ejemplo.remove(20);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}",ejemplo.toString());
		ejemplo.remove(20);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {30, {15, ∅, ∅}, ∅}}",ejemplo.toString());
	}
	
	@Test
	public void testRemoveHoja() {
		ejemplo.remove(30);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, ∅}}",ejemplo.toString());
	}
	
	@Test
	public void testRemove1Hijo() {
		ejemplo.remove(5);
		Assert.assertEquals("{10, {2, ∅, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}",ejemplo.toString());
	}
	
	@Test
	public void testRemove2Hijos() {
		ejemplo.remove(10);
		Assert.assertEquals("{15, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}",ejemplo.toString());
	}
	
		
	@Test
	public void testTagHeightEjemplo() {
			other.tagHeight();
			other.filterTags("height");
			Assert.assertEquals("{10 [(height, 1)], {5 [(height, 2)], {2 [(height, 3)],"+" ∅, ∅}, ∅}, {20 [(height, 2)], {15 [(height, 3)], {12 [(height, 4)],"+" ∅, ∅}, ∅}, ∅}}",other.toString());
		}
				
				
		@Test(expected = IllegalArgumentException.class)
		public void testInsertException() {
			Integer i = null;
			other.insert(i);	
		}
		
	
		@Test(expected = IllegalArgumentException.class)
		public void testContainsNull() {
			other.contains(null);
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void testRemoveNullElement() {
			Integer i = null;
			other.remove(i);
		}
		
		@Test(expected = NoSuchElementException.class)
		public void testRemoveNoSuchElement() {
			other.remove(11);
		}
	}


