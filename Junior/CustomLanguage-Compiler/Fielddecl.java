class Fielddecl implements Token
{
    int choice;
    Optionalfinal of;
    Type ty;
    String id;
    OptionalAsn asn;
    int iliteral;
    String key = "";

    public Fielddecl(Boolean b, Type t, String i, OptionalAsn a)
    {
        ty = t;
	id = i;
	asn = a;
        choice = 0;
	if(b)
	    key = "final";
    }

    public Fielddecl(Type t, String i, int l)
    {
        ty = t;
	id = i;
	iliteral = l;
        choice = 1;
    }

    public String toString(int t)
    {
        String ret = "";
        if (choice == 0)
            ret = key + " " + ty.toString(t) + " " + id + asn.toString(t)+ ";\n";
        else
            ret = ty.toString(t) + " " + id + " [ " + iliteral + " ];\n";
        return ret;
    }
}
