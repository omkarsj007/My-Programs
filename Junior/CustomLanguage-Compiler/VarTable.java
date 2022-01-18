import java.util.Vector;
import java.util.Iterator;

class VarTable
{
  class Pair
  {
      String key, value;
      int scope;
      boolean arr;
      boolean fin;
      boolean func;
      public Pair(String a, String b, int s)
      {
	  key = a;
	  value = b;
	  scope = s;
	  arr = false;
	  fin = false;
	  func = false;
      }
      public String getKey()
      {
	  return key;
      }
      public String getValue()
      {
	  return value;
      }
      public int getScope()
      {
	  return scope;
      }
  }
    Vector<Pair> table;
    public VarTable()
    {
	table = new Vector<Pair>();
    }
    public String getType(String s, int sc)
    {
	for (Pair p : table)
	    {
		if (p.getKey().equals(s) && p.getScope() ==  sc )
		    return p.getValue();
		else if (p.getKey().equals(s))
		    return p.getValue();
	    }
	return "";
    }

    public void setFinal(String s, int sc)
    {
	for (Pair p : table)
	    {
		if (p.getKey().equals(s) && p.getScope() ==  sc )
		    p.fin = true;
	    }
    }

    public boolean getFinal(String s, int sc)
    {
	for (Pair p : table)
	    {
		if (p.getKey().equals(s) && p.getScope() ==  sc )
		    return p.fin;
		else if (p.getKey().equals(s))
		    return p.fin;
	    }
	return false;
    }

    public void setArr(String s, int sc)
    {
	for (Pair p : table)
	    {
		if (p.getKey().equals(s) && p.getScope() ==  sc )
		    p.arr = true;
	    }
    }

    public boolean getArr(String s, int sc)
    {
	for (Pair p : table)
	    {
		if (p.getKey().equals(s) && p.getScope() ==  sc )
		    return p.arr;
		else if (p.getKey().equals(s))
		    return p.arr;
	    }
	return false;
    }

    public void setFunc(String s, int sc)
    {
	for (Pair p : table)
	    {
		if (p.getKey().equals(s) && p.getScope() ==  sc )
		    p.func = true;
	    }
    }

    public boolean getFunc(String s, int sc)
    {
	for (Pair p : table)
	    {
		if (p.getKey().equals(s) && p.getScope() ==  sc )
		    return p.func;
		else if (p.getKey().equals(s))
		    return p.func;
	    }
	return false;
    }


    public void removeScope(int sc)
    {
	Iterator<Pair> itr = table.iterator();
	while(itr.hasNext())
	  {
	      Pair p = itr.next();
	      int x = p.getScope();
	      if (x == sc)
		  itr.remove();
	  }
    }
	
	
    public boolean add(String id, String t, int sc)
    {
	for (Pair p : table)
	    {
		if (p.getKey().equals(id) && p.getScope() == sc)
		    return false;
	    }
	table.add(new Pair(id,t,sc));
	return true;
    }
  
}
