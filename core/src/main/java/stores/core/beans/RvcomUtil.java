
package stores.core.beans;

import org.apache.sling.api.resource.ResourceResolver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class for RVCOM application.
 */
public class RvcomUtil {

    /** The Constant ALTTEXT. */
    public static final String ALTTEXT = "alttext.";

    /** The Constant CARD_SUBTYPE_REGEXPR. */
    public static final String CARD_SUBTYPE_REGEXPR = "^[a-zA-Z,]+$";

    /** The Constant ALPHA_REGEXPR. */
    public static final String ALPHA_REGEXPR = "^[a-zA-Z]+$";

    /** The Constant SPECIAL_CHARACTER_REGEXP. */
    public static final String SPECIAL_CHARACTER_REGEXP = "[@#$%^<>]|[{}()<>\\[\\]]";

    /** The Constant SCRIPT_REGX. */
    public static final String SCRIPT_REGX = "[ ]*(\\/)?(.*)?script([ ]+.*|[^\\w]*)";

    /** The Constant EMAIL_PATTERN. */
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /** The Constant ALLOWED_PATTERN. */
    private static final String ALLOWED_PATTERN = "[^a-zA-Z0-9\\.\\_\\+\\&\\-\\(\\)\\/\\,\\:\\s*]";

    /** The resource resolver. */
    ResourceResolver resourceResolver = null;

    /** The Constant log. */
    //private static final Logger log = LoggerFactory.getLogger(RvcomUtil.class);

    /**
     * Checks if is characters allowed.
     *
     * @param input the input
     * @return true, if is characters allowed
     */
    public boolean isCharactersAllowed(String input) {
        if (input == null)
            return true;

        return (hasScriptRegEx(input) ? false : !hasIllegalCharacters(input));

    }

    /**
     * Checks for illegal characters.
     *
     * @param input the input
     * @return true, if successful
     */
    public boolean hasIllegalCharacters(String input) {
        Pattern p = Pattern.compile(SPECIAL_CHARACTER_REGEXP);
        Matcher m = p.matcher(input);
        boolean hasIllegalCharacters = m.find();
        return hasIllegalCharacters;
    }

    /**
     * Checks for script reg ex.
     *
     * @param input the input
     * @return true, if successful
     */
    public boolean hasScriptRegEx(String input) {
        Pattern p = Pattern.compile(SCRIPT_REGX);
        Matcher m = p.matcher(input);
        boolean hasIllegalCharacters = m.find();
        return hasIllegalCharacters;
    }

    public static void main(String[] args) {
        RvcomUtil rv = new RvcomUtil();
        boolean isAllowed = rv.isCharactersAllowed("wht's fault");
        System.out.println(isAllowed);
    }


}
