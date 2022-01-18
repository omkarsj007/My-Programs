class StmtList implements Token
{
  StmtList stmtList;
  Stmt stmt;
  public StmtList()
  {
    stmtList = null;
    //stmt = s;
  }
  public StmtList(Stmt s, StmtList sl)
  {
    stmtList = sl;
    stmt = s;
  }

  public String toString(int t)
  {
    if (stmtList != null)
      return stmt.toString(t) + stmtList.toString(t);
    else
      return "";
  }

    public boolean typeCheck(VarTable table, int scope, String type) throws ExampleException
    {
	
	if(stmtList != null)
	    {
		return (stmt.typeCheck(table, scope, type) || stmtList.typeCheck(table, scope, type));
	    }
	return false;
    }
}
