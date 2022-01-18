class Optionalfinal implements Token
{
    int choice;

    public Optionalfinal(boolean b)
    {
        choice = 0;
    }

    public Optionalfinal()
    {
        choice = 1;
    }

    public String toString(int t)
    {
        String ret = "";
        if (choice == 0)
            ret = " final ";
        
        return ret;
    }
}
