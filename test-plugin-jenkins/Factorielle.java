public class Factorielle {
    
    public static int calcFactorielle(int n){
        if(n>1){
            return n*calcFactorielle(n-1);
        }
        return 1;
    }

    public static void main(String[] args) {
        
        System.out.println(calcFactorielle(args[0]));
    }
}