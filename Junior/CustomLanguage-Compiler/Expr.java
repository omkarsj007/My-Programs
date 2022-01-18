class Expr implements Token
{
  int thisId;
  BinaryOp binOp;
  BooleanOp boolOp;
  int intLit;
  String id;
  String symbol;
  Name n;
  float f;
  boolean true_false;
  Args a;
  Expr e;
  Expr e2;
  Expr e3;
  Type type;

  public Expr(BinaryOp b)
  {
    binOp = b;
    thisId = 0;
  }
  public Expr(int i)
  {
    intLit = i;
    thisId = 1;
  }
  public Expr(String i)
  {
    id = i;
    thisId = 2;
  }
  public Expr(Name nd)
  {
    n = nd;
    thisId = 3;
  }
    public Expr(String i, Args ad)
    {
	id = i;
	a = ad;
	thisId = 4;
    }
    
    public Expr(String s, String n)
    {
      id = s;
      thisId = 5;
    }
    
    public Expr(float fd)
    {
      f = fd;
      thisId = 6;
    }
    
    public Expr(boolean x)
    {
	true_false = x;
      if(x)
      thisId = 7;
      else
      thisId = 8;
    }
    
    public Expr(Expr ed)
    {
      e = ed;
      thisId = 9;
    }
    
    public Expr(String sym, Expr ed)
    {
      symbol = sym;
      e = ed;
      thisId = 10;
    }
    
    public Expr(Type td, Expr ed)
    {
      type = td;
      e = ed;
      thisId = 11;
    }
    
    public Expr(Expr ed, Expr ed2, Expr ed3)
    {
      e = ed;
      e2 = ed2;
      e3 = ed3;
      thisId = 12;
    }

    public Expr(BooleanOp b)
    {
	boolOp = b;
	thisId = 13;
    }
 
    public Expr(String s, Boolean b)
    {
	id = s;
	thisId = 14;
    }

  public String toString(int t)
  {
    String ret = "";
    if (thisId == 0)
      ret = "(" + binOp.toString(t) + ")";
    else if (thisId == 1)
      ret = Integer.toString(intLit);
    else if (thisId == 2)
      ret = id + "()";
    else if(thisId == 3)
      ret = n.toString(t);
    else if(thisId == 4)
	    ret = id + "(" + a.toString(t) + ")";
    else if(thisId == 5)
	    ret = "\"" + id + "\"";
    else if(thisId == 6)
	    ret = Float.toString(f);
    else if(thisId == 7)
	    ret = "true";
    else if(thisId == 8)
	    ret = "false";
    else if(thisId == 9)
	    ret = "(" + e.toString(t) + ")";
    else if(thisId == 10)
	    ret = symbol + e.toString(t);
    else if(thisId == 11)
	    ret = "(" + type.toString(t) + ")" + e.toString(t);
    else if(thisId == 12)
	    ret = "(" + e.toString(t) + "?" + e2.toString(t) + ":" + e3.toString(t) + ")";
    else if(thisId == 13)
	ret = "(" +boolOp.toString(t) + ")";
    else if(thisId == 14)
	ret = "'" + id + "'";
    return ret;
  }
}
