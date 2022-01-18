class OptionalAsn implements Token
{
  Expr expr;
  boolean exists;
  public OptionalAsn(Expr e)
  {
    expr = e;
    exists = true;
  }
  public OptionalAsn()
  {
    exists = false;
  }
  public String toString(int t)
  {
    if (!exists)
      return "";
    return " = " + expr.toString(t);
  }

    public String typeCheck(VarTable table, int scope) throws ExampleException
    {
	if(!exists)
	    {
		return "noAssignment";
	    }
	else
	    return expr.typeCheck(table,scope);
    }
}
