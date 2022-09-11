package nl.jixxed.eliteodysseymaterials.helper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ClipboardHelperTest {

    private final String testmessage = """
            {
             	"version":1,
             	"items": [
             		{
             			"item": "Hpt_BeamLaser_Fixed_Small",
             			"blueprint": "Weapon_Efficient",
             			"grade": 5,
             			"highestGradePercentage":0.8
             		},
             		{
             			"item": "Hpt_BeamLaser_Fixed_Small",
             			"blueprint": "special_weapon_damage"
             		}
             	]
            }""";

    @Test
    public void testCompress() {
        final String s = ClipboardHelper.convertJsonToBase64Compressed(this.testmessage);
        Assertions.assertThat(s).isEqualTo("eJyljssKwjAQRdftV5Ssi-hCkC4FHwsXggsXImFMp-lA0oYkPqD0353Y_oGbC_dwmLlDXmTihT5Q34lqVaZKEW0QVXHjkg0pJsZIHF2UWwR7goBe7umDtbxYMEaUk_gwT3SeupjsK4LrO7lrGlKEzGZJe6iRhfXcW9IthnhI-IxesQqaheVik4Sx_HtJcKgIjHxPi2qw6cHvOMc9H7-R-kuw");

    }

}