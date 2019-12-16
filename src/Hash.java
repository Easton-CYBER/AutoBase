//import java.nio.charset.*;
//import java.security.*;

//hashing algo used when creating users and logging in.
/*
class Hash{
    private String passToHash;
    private SecureRandom secrand;
    private MessageDigest md;

    private byte[] salt;
    private byte[] hashed_password;
    private String print_hash;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Hash hash = new Hash();
        hash.Hash_Pass();
    }

    public void Hash_Pass() throws NoSuchAlgorithmException {
        try{
            passToHash = HashedPassword();
            secrand = new SecureRandom();
            salt = new byte[16];
            
            secrand.nextBytes(salt);

            md = MessageDigest.getInstance("SHA-512");
            md.update(salt);

            hashed_password = md.digest(passToHash.getBytes(StandardCharsets.UTF_8));

            print_hash = hashed_password.toString();
        }catch(NoSuchAlgorithmException el){
            System.out.println("Could no hash password");
        }
    }

    public String HashedPassword(){
        passToHash = "hispassishashed";

        return passToHash;
    }

    
    puname = txuser.getText();
        ppaswd = pass.getPassword();
        getpass = String.valueOf(ppaswd);

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(getpass.getBytes());
        
            byte[] salt = md.digest();
            StringBuilder sb = new StringBuilder();
            int q;
            for(q=0; q < salt.length; q++){
                sb.append(Integer.toString((salt[q] & 0xff) + 0x100, 16).substring(1));
            }

            hashedpass = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
}
*/
