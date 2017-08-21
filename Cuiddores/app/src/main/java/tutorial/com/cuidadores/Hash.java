package tutorial.com.cuidadores;

/**
 * Es la clase encargada de realizar el Hash de la clave de conexión.
 * Se realiza el hash para ofrecer seguridad, comprobando que el hash de ambas contraseñas,
 * la del cuidador y la del paciente, coinciden.
 * Si éstas no coinciden, la conexión no se establece.
 * El código está disponible en :
 * http://programacionextrema.com/2015/10/28/encriptar-en-md5-y-sha1-con-java/
 */
public class Hash
{
    public static String getHash(String txt, String hashType)
    {
        try
        {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i)
            {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Calcula un hash MD5 a partir de un texto
    public static String md5(String txt)
    {
        return Hash.getHash(txt, "MD5");
    }
}