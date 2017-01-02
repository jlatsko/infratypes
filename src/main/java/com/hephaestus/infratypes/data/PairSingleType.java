package com.hephaestus.infratypes.data;

import java.util.Vector;
class PairSingleType<T> extends Vector<T>
{
  public PairSingleType(Vector<T> a) throws ClassCastException
  {
    if (a.size() != 2) throw new ClassCastException("Got Vector of size=" +a.size() +", expected size=2");
    super.add(a.get(0));
    super.add(a.get(1));
  }
 
  public PairSingleType(T a, T b)
  {
    super.add(a);
 
    super.add(b);
  }
 
  private static boolean equals(Object x, Object y) {
    return (x == null && y == null) || (x != null && x.equals(y));
  }
 
  public boolean equals(Object other) {
     return
      other instanceof PairSingleType &&
      equals(get(0), ((PairSingleType)other).get(0)) &&
      equals(get(1), ((PairSingleType)other).get(1));
  }
 
  public boolean equals(PairSingleType<T> v) { return get(0).equals(v.get(0)) && get(1).equals(v.get(1)); }
 
  public T set(int i, T v) throws IndexOutOfBoundsException
  {
    if (i!=0 && i!=1) throw new IndexOutOfBoundsException("Got index argument " +i +" but should get 0 or 1");
    return super.set(i, v);
  }
 
  public T setFirst(T v) { return super.set(0, v); }
  public T setSecond(T v) { return super.set(1, v); }
 
  public T getFirst() { return super.get(0); }
  public T getSecond() { return super.get(1); }
 
  public int hashCode() {
    if (get(0) == null) return (get(1) == null) ? 0 : get(1).hashCode() + 1;
    else if (get(1) == null) return get(0).hashCode() + 2;
    else return get(0).hashCode() * 17 + get(1).hashCode();
  }
 
  public static <T> PairSingleType<T> of(T a, T b) {
    return new PairSingleType<T>(a,b);
  }
 
  public String toString() {
    return "PairSingleType[" + get(0) + "," + get(1) + "]";
  }
 
  // The big difference between PairSingleType<T> and Pair<A, B> is 
  // that PairSingleType<T> has integer based get and set methods
}