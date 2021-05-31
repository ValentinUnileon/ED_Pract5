package ule.edi.tree;
  
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * arbol binario de busqueda (binary search tree, BST).
 * 
 * El codigo fuente esta en UTF-8, y la constante EMPTY_TREE_MARK definida en
 * AbstractTreeADT del proyecto API deberia ser el simbolo de conjunto vacio:
 * ∅
 * 
 * Si aparecen caracteres "raros", es porque el proyecto no esta bien
 * configurado en Eclipse para usar esa codificacion de caracteres.
 *
 * En el toString() que esta ya implementado en AbstractTreeADT se usa el
 * formato:
 * 
 * Un arbol vaci­o se representa como "∅". Un Ã¡rbol no vacio como
 * "{(informacion rai­z), sub-arbol 1, sub-arbol 2, ...}".
 * 
 * Por ejemplo, {A, {B, ∅, ∅}, ∅} es un arbol binario con rai­z "A" y un
 * unico sub-arbol, a su izquierda, con rai­z "B".
 * 
 * El metodo render() tambien representa un arbol, pero con otro formato; por
 * ejemplo, un arbol {M, {E, ∅, ∅}, {S, ∅, ∅}} se muestra como:
 * 
 * M 
 * | E
 * | | ∅
 * | | ∅ 
 * | S 
 * | | ∅
 * | | ∅
 * 
 * Cualquier nodo puede llevar asociados pares (clave,valor) para adjuntar
 * informacion extra. Si es el caso, tanto toString() como render() mostraran
 * los pares asociados a cada nodo.
 * 
 * Con {@link #setTag(String, Object)} se inserta un par (clave,valor) y con
 * {@link #getTag(String)} se consulta.
 * 
 * 
 * Con <T extends Comparable<? super T>> se pide que exista un orden en los
 * elementos. Se necesita para poder comparar elementos al insertar.
 * 
 * Si se usara <T extends Comparable<T>> seria muy restrictivo; en su lugar se
 * permiten tipos que sean comparables no solo con exactamente T sino tambien
 * con tipos por encima de T en la herencia.
 * 
 * @param <T> tipo de la informacion en cada nodo, comparable.
 */
public class BinarySearchTreeImpl<T extends Comparable<? super T>> extends AbstractBinaryTreeADT<T> {

	BinarySearchTreeImpl<T> father; // referencia a su nodo padre)
	int count;  // contador de instancias 

	/**
	 * Devuelve el arbol binario de busqueda izquierdo.
	 */
	protected BinarySearchTreeImpl<T> getLeftBST() {
		// El atributo leftSubtree es de tipo AbstractBinaryTreeADT<T> pero
		// aqui­ se sabe que es ademas BST (binario de busqueda)
		//
		return (BinarySearchTreeImpl<T>) leftSubtree;
	}

	private void setLeftBST(BinarySearchTreeImpl<T> left) {
		this.leftSubtree = left;
	}

	/**
	 * Devuelve el arbol binario de busqueda derecho.
     */
	protected BinarySearchTreeImpl<T> getRightBST() {
		return (BinarySearchTreeImpl<T>) rightSubtree;
	}

	private void setRightBST(BinarySearchTreeImpl<T> right) {
		this.rightSubtree = right;
	}

	/**
	 * arbol BST vaci­o
	 */
	public BinarySearchTreeImpl() {
					
		this.father=null;																//DUDA hay que poner le contador a cero?
		this.count=0;
	
	}

	public BinarySearchTreeImpl(BinarySearchTreeImpl<T> father) {
		// TODO HACER QUE THIS SEA EL NODO VACIO, asignando como padre el parametro			
		this.father=father;
		this.count=0;
	
	}

	private BinarySearchTreeImpl<T> emptyBST(BinarySearchTreeImpl<T> father) {
		//Devuelve un nodo vacío
		return new BinarySearchTreeImpl<T>(father);
	}

	
	/**
	 *  devuelve la cadena formada por el contenido del árbol teniendo en cuenta que 
	 *  si un nodo tiene su atributo count>1 pone entre paréntesis su valor justo detrás del atributo elem
	 *  También debe mostrar las etiquetas que tenga el nodo (si las tiene)
	 *  
	 *  CONSEJO: REVISAR LA IMPLEMENTACIÓN DE TOSTRING DE LA CLASE AbstractTreeADT 
	 * 
	 * Por ejemplo: {M, {E(2), ∅, ∅}, {K(5), ∅, ∅}}
	 * 
	 * @return cadena con el contenido del árbol incluyendo su atributo count entre paréntesis si elemento tiene más de 1 instancia
	 */
	public String toString() {
		
		if (! isEmpty()) {
			//	Construye el resultado de forma eficiente
			StringBuffer result = new StringBuffer();
				
			//	Raíz
			result.append("{" + content.toString());
			
			if(count>1) {
				result.append("("+ count + ")");
			}
			
			if (! tags.isEmpty()) {
				result.append(" [");
				
				List<String> sk = new LinkedList<String>(tags.keySet());
				
				Collections.sort(sk);
				for (String k : sk) {
					result.append("(" + k + ", " + tags.get(k) + "), ");
				}
				result.delete(result.length() - 2, result.length());
				result.append("]");
			} 
			
			//	Y cada sub-árbol
			for (int i = 0; i < getMaxDegree(); i++) {
				result.append(", " + getSubtree(i).toString());
			}
			//	Cierra la "}" de este árbol
			result.append("}");
			
			return result.toString();
		} else {
			return AbstractTreeADT.EMPTY_TREE_MARK;
		}
		
	}
	
	/**
	 * Inserta los elementos que no sean null, de una coleccion en el arbol. 
	 * (si alguno es 'null', no lo inserta)
	 * 
	 * No se permiten elementos null.
	 * 
	 * @param elements valores a insertar.
	 * @return numero de elementos insertados en el arbol (elementos diferentes de null)
	 */
	public int insert(Collection<T> elements) {
		
		int numero=0;
		
		for(T elem:elements) {							//test
		
			if(elem!=null) {
				insert(elem);
				numero++;
				
			}
		}
		
	
		return numero;
	}

	/**
	 * Inserta los elementos que no sean null, de un array en el arbol. 
	 * (si alguno es 'null', no lo inserta)
	 * 
	 * No se permiten elementos null.
	 * 
	 * @param elements elementos a insertar.
	 * @return numero de elementos insertados en el arbol (elementos diferentes de null)
	 */
	public int insert(T... elements) {
		
		int numero=0;
		
		for(T elem:elements) {
		
			if(elem!=null) {
				insert(elem);
				numero++;
				
			}
		}
		
	
		return numero;
	}

	/**
	 * Inserta (como hoja) un nuevo elemento en el arbol de busqueda.
	 * 
	 * Debe asignarse valor a su atributo father (referencia a su nodo padre o null
	 * si es la rai­z)
	 * 
	 * No se permiten elementos null. Si element es null dispara excepcion:IllegalArgumentException 
	 * Si el elemento ya existe en el arbol
	 *  no inserta un nodo nuevo, sino que incrementa el atributo count del nodo que tiene igual contenido.
	 * 
	 * @param element valor a insertar.
	 * @return true si se insertó en un nuevo nodo (no existia ese elemento en el arbol),
	 *         false en caso contrario
	 * @throws IllegalArgumentException si element es null
	 */
	public boolean insert(T element) {
		
		
		
		if(element==null) {
			throw new IllegalArgumentException("Elemento nulo");
		}
		
		BinarySearchTreeImpl<T> puntero=this; 
		
		boolean resultado=false;
		
		
		
		boolean fin=false;
											//se puede utilizar le metodo isLeaf
		while(!fin) {						//busco el sitio disponible para añadir el elemento
			
			if(puntero.content==null) {		//se añade en el primer nodo
				 
				//se añade aqui 
				
				puntero.content=element;
				puntero.count= puntero.count+1;
				fin=true;
				resultado=true;
				
				
				BinarySearchTreeImpl<T> derecha= new BinarySearchTreeImpl(puntero);
				puntero.setRightBST(derecha);
				
				BinarySearchTreeImpl<T> izquierda= new BinarySearchTreeImpl(puntero);
				puntero.setLeftBST(izquierda);
				
				
				
				
				
				
				
			}else {						//siguiente nodo
										//averguar si derecho o izquierdo
				
				if(puntero.content.compareTo( element ) == 1 ) {										//DUDA
					
					puntero=puntero.getLeftBST();			
					
				}else if(puntero.content.compareTo( element ) == -1) {
					
					puntero=puntero.getRightBST();
						
					
				}else if(puntero.content.compareTo( element ) == 0) {
					
					//se aumenta el contador
					puntero.count++;
					fin=true;
					resultado=false;
					
					
				}
				
			}
				
		}
		
		return resultado;			
	
	 
	}
	

	/**
	 * Busca el elemento en el arbol.
	 * 
	 * No se permiten elementos null.
	 * 
	 * @param element valor a buscar.
	 * @return true si el elemento esta en el arbol, false en caso contrario
	 * @throws IllegalArgumentException si element es null
	 *
	 */
	public boolean contains(T element) {
		
		if(element==null) {
			throw new IllegalArgumentException("Elemento nulo");
		}
		
		BinarySearchTreeImpl<T> puntero=this; 
		
		boolean encontrado=false;
		boolean resultado=false;
		
		while(!encontrado) {
			
			if(puntero.content==null) {
				encontrado=true;
				
			}else {
				
				if(puntero.content.compareTo( element ) == 1 ) {										//DUDA
					
					puntero=puntero.getLeftBST();			
					
				}else if(puntero.content.compareTo( element ) == -1) {
					
					puntero=puntero.getRightBST();
						
					
				}else if(puntero.content.compareTo( element ) == 0) {

					encontrado=true;
					resultado=true;	
					
				}
			
			}
			
		}

		return resultado;
	}

	/**
	 * Elimina los valores en un array del Arbol.
	 * Devuelve el número de elementos que pudo eliminar del árbol
	 *  (no podrá eliminar los elemenots 'null' o que no los contiene el arbol)
	 * 
	 * return numero de elementos eliminados del arbol
	 */
	public int  remove(T... elements) {
		// TODO Implementar el metodo
		return 0;
	}

	/**
	 * Elimina un elemento del arbol. Si el atributo count del nodo que contiene el elemento es >1, simplemente se decrementará este valor en una unidad
	 * 
	 * Si hay que eliminar el nodo, y tiene dos hijos, se tomara el criterio de sustituir el
	 * elemento por el menor de sus mayores y eliminar el menor de los mayores.
	 * 
	 * @throws NoSuchElementException si el elemento a eliminar no esta en el arbol
	 * @throws IllegalArgumentException si element es null
     *
	 */
	public void remove(T element) {	//recursivo
		
		if(element==null) {
			throw new IllegalArgumentException("Elemento nulo");
		}
		
		boolean encontrado=false;
		
		boolean resultado=false;
		
		
		BinarySearchTreeImpl<T> puntero=this; 
	
		
		while(!encontrado) {
			
			if(puntero.content==null) {
				encontrado=true;
				resultado=true;
				
			}else {
				
				if(puntero.content.compareTo( element ) == 1 ) {										//DUDA
					
					puntero=puntero.getLeftBST();			
					
				}else if(puntero.content.compareTo( element ) == -1) {
					
					puntero=puntero.getRightBST();
						
					
				}else if(puntero.content.compareTo( element ) == 0) {

					encontrado=true;
					resultado=false;
					
					if(puntero.count>1) {
						
						
						// en este caso solamente se decrementa el contador
						puntero.count--;
						
						
						
						
					}else {
						
						//en este caso se elimina el nodo y se debe comprobar los hijos que tiene

						
						if(puntero.getLeftBST()!=null && puntero.getRightBST()!=null) {
							
							//se escoge el menor de los mayores
							
							
							
						}else if(puntero.getLeftBST()==null && puntero.getRightBST()!=null) {
						
							//solo hijo derecho
							
						}else if(puntero.getLeftBST()!=null && puntero.getRightBST()==null) {
							//solo hijo izquierdo
							
							puntero.content=puntero.getLeftBST().content;
							puntero.count=puntero.getLeftBST().count;
							puntero.setRightBST(  puntero.getLeftBST().getRightBST()  );
							puntero.setLeftBST(puntero.getLeftBST().getLeftBST());
							
							
							
							
						
						}
						
					
					}
			
					
				}
			
			}
			
		}
		
		
		
		
		
		
		
		
		
		if(resultado) {
			throw new NoSuchElementException("Elemento no encontrado");
		}
		
		
		
	}
	
	/**
	 * Decrementa el número de instancias del elemento en num unidades.
	 * Si count queda en cero o negativo, se elimina el elemento del arbol. 
	 * 
	 * 
	 * Si hay que eliminar el nodo, y tiene dos hijos, se tomara el criterio de sustituir el
	 * elemento por el menor de sus mayores y eliminar el menor de los mayores.
	 * 
	 * @throws NoSuchElementException si el elemento a eliminar no esta en el arbol	
	 * @throws IllegalArgumentException si element es null
	 */
	public void remove(T element, int num) {
		// TODO Implementar el metodo
	}

	/**
	 * Importante: Solamente se puede recorrer el arbol una vez
	 * 
	 * Etiqueta cada nodo con la etiqueta "height" y el valor correspondiente a la
	 * altura del nodo.
	 * 
	 * Por ejemplo, sea un arbol "A":
	 * 
	 * {10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}
	 * 
	 * 10
	 * | 5 
	 * | | 2
	 * | | | ∅ 
	 * | | | ∅ 
	 * | | ∅ 
	 * | 20 
	 * | | 15 
	 * | | | ∅
	 * | | | ∅ 
	 * | | 30 
	 * | | | ∅ 
	 * | | | ∅
	 * 
	 * 
	 * el arbol quedara etiquetado:
	 * 
	 * {10 [(height, 1)], 
	 * {5 [(height, 2)], {2 [(height, 3)],∅, ∅}, ∅}, 
	 * {20[(height, 2)], {15 [(height, 3)], {12 [(height, 4)], ∅, ∅}, ∅}, ∅}}
	 * 
	 */
	public void tagHeight() {
		// TODO implementar el mÃ©todo
	}


	
	
	/**
	 * Devuelve un iterador que recorre los elementos (sin tener en cuenta el número de instancias)del arbol por niveles segun
	 * el recorrido en anchura
	 * 
	 * Por ejemplo, con el arbol
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40, ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * y devolvera el iterador que recorrera los nodos en el orden: 50, 30, 80, 10, 40, 60
	 * 
	 * 
	 * 
	 * @return iterador para el recorrido en anchura
	 */
    public Iterator<T> iteratorWidth() {
	 	// TODO Implementar mÃ©todo
		// puede implementarse creando una lista con el recorrido en anchura de los
		// elementos del arbol y devolver el iterador de dicha lista
		return null;
	}

	/**
	 * Devuelve un iterador que recorre los elementos (teniendo en cuenta el número de instancias)del arbol por niveles segun
	 * el recorrido en anchura
	 * 
	 * Por ejemplo, con el arbol
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40, ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * y devolvera el iterador que recorrera los nodos en el orden: 50, 30, 30, 80, 80, 10, 40, 60
	 *  
	 * @return iterador para el recorrido en anchura
	 */
     public Iterator<T> iteratorWidthInstances() {
		// TODO Implementar mÃ©todo
		// puede implementarse creando una lista con el recorrido en anchura de los
		// elementos del arbol (teniendo el número de instancias que tiene el elemento)
		//y devolver el iterador de dicha lista
		return null;
	 }
	
	// METODOS AÑADIDOS DESDE LA PRIMERA VERSION DEL PROYECTO
	
	/**
	 * Cuenta el número de elementos diferentes del arbol (no tiene en cuenta las instancias)
	 * 
	 * Por ejemplo, con el arbol
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40(4), ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * la llamada a ejemplo.instancesCount() devolvera 6
	 * 
	 * @return el numero de elementos diferentes del arbol 
	 */
    public int size() {
		// TODO implementar este metodo
		return 0;
	}
	
    /**
	 * Cuenta el número de instancias de elementos diferentes del arbol 
	 * 
	 * Por ejemplo, con el arbol ejemplo=
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40(4), ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * la llamada a ejemplo.instancesCount() devolvera 11
	 * 
	 * @return el número de instancias de elementos del arbol 
	 */
	public int instancesCount() {
		// TODO implementar este metodo
		return 0;
	}
	
	/**
	 * Devuelve el sub-árbol indicado. (para tests)
	 * path será el camino para obtener el sub-arbol. Está formado por L y R.
	 * Si se codifica "bajar por la izquierda" como "L" y
	 * "bajar por la derecha" como "R", el camino desde un 
	 * nodo N hasta un nodo M (en uno de sus sub-árboles) será la
	 * cadena de Ls y Rs que indica cómo llegar desde N hasta M.
     *
     * Se define también el camino vacío desde un nodo N hasta
     * él mismo, como cadena vacía.
     *  
     *  Por ejemplo, con el arbol ejemplo=
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40(4), ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * la llamada a ejemplo.getSubtreeWithPath("LL").toString() devolvera "{10, ∅, ∅}"
	 * la llamada a ejemplo.getSubtreeWithPath("R").toString() devolvera "{80(2), {60, ∅, ∅}, ∅}"
	 * la llamada a ejemplo.getSubtreeWithPath("").toString() devolvera "{50, {30(2), {10, ∅, ∅}, {40(4), ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}"
	 * la llamada a ejemplo.getSubtreeWithPath("RR").toString() disparará excepción NoSuchElementException
	 * 
	 * Si el subarbol no existe lanzará la excepción NoSuchElementException.
	 * 
	 * @param path 
	 * @return
	 * @throws NoSuchElementException si el subarbol no existe
	 */
	public BinarySearchTreeImpl<T> getSubtreeWithPath(String path) {
		//TODO implementar el método
		
		return null;
		
	}	
	
	/**
	 * Importante: Solamente se puede recorrer el arbol una vez
	 * 
	 * Recorre en preorden el arbol etiquetando todos sus nodos con la etiqueta "preorden" y
	 * el valor correspondiente a la posición en dicho recorrido.
	 * 
	 * 
	 * Por ejemplo, sea el arbol ejemplo
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40(4), ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * la llamada a ejemplo.tagPosPreorden el arbol quedaria etiquetado:
	 * 
     *  {50 [(preorden, 1)], {30(2) [(preorden, 2)], {10 [(preorden, 3)], ∅, ∅}, 
     *   {40(4) [(preorden, 4)], ∅, ∅}}, {80(2) [(preorden, 5)], {60 [(preorden, 6)], ∅, ∅}, ∅}}
	 * 
	 */
	public void tagPosPreorden() {
		// TODO implementar este metodo
	
	}
	
	
	/**
	 * Importante: Solamente se puede recorrer el arbol una vez
	 * 
	 * Calcula y devuelve el numero de nodos que son hijos unicos y etiqueta cada
	 * nodo que sea hijo unico (no tenga hermano hijo del mismo padre) con la
	 * etiqueta "onlySon" y el valor correspondiente a su posicion segun el
	 * recorrido postorden en este arbol.
	 * 
	 * La rai­z no se considera hijo unico.
	 * 
	 * Por ejemplo, sea un arbol "A", que tiene 3 hijos unicos, los va etiquetando
	 * segun su recorrido en postorden.
	 * 
	 * {30, {10, {5, {2, ∅, ∅}, ∅}, {20, {15, {12, ∅, ∅}, ∅}, ∅}, ∅}
	 * 
	 *
	 * el arbol quedari­a etiquetado:
	 * 
	 * {30, {10 [(onlySon, 4)], {5, {2 [(onlySon, 1)],	 ∅, ∅}, ∅}, {20, {15 [(onlySon, 3)], {12
	 * [(onlySon, 2)], ∅, ∅}, ∅}, ∅}, ∅}
	 * 
	 */
	public int tagOnlySonPostorder() {
		// TODO implementar este metodo
		return 0;
	
	}
	
	/**
	 * Busca y devuelve el elemento simétrico del pasado como parámetro en el árbol 
	 * Primero debe encontrarlo y luego comprueba si el nodo que contiene ese elemento
	 * tiene nodo simétrico en la otra rama de la raíz.
     * Debe recorrer desde el nodo que contiene el elemento,  hasta su simétrico etiquetando los nodos del camino 
     * con un número consecutivo empezando por el 1 en el this. 
     * 
     * Por ejemplo: para el árbol ejemplo= {10, {5, {2, ∅, ∅}, {7,∅, ∅},}, {20, {15, {12, ∅, ∅}, ∅ },{30, ∅, ∅}}}. 
     * 
     * Si se hace ejemplo.getSimetrical() devolverá el elemento 15 y etiquetará los nodos 7, 5, 10, 20, 15 con numeros consecutivos
     *  y la etiqueta road. 
     *  
     * Así el árbol quedaría etiquetado: 10 [(road, 3)],{5[(road, 2)], {2, ∅, ∅}, {7 [(road, 1)],∅, ∅},}, {20 [(road, 4)], {15 [(road, 5)], {12, ∅, ∅}, ∅},{30, ∅, ∅}}}
     *  siendo el 15 su nodo simétrico en el árbol.
	 * 
	 * @throws NoSuchElementException si el elemento a comprobar no esta en el arbol	
	 * @throws IllegalArgumentException si element es null
	 */
	public T getSymetrical(T elem) {
		// TODO implementar este metodo
		return null;
		
	}
	
	/**
	 * Crea y devuelve un árbol exactamente igual que el this
	 * 
	 * @return un arbol exactamente igual (misma estructura y contenido) que el arbol this
	 */
	public BinarySearchTreeImpl<T> copy(){
		// TODO implementar este metodo
		return null;
		
	}
	
	
	
}
	
	