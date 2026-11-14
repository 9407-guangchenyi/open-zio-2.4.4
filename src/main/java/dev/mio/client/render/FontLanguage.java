/*
 * ─────────────────────────────────────────────
 * Licensed under the MIT License.
 *
 * Zio is not affiliated with or endorsed by Mio.
 * Provided "as is", without warranty of any kind.
 * ─────────────────────────────────────────────
 */

package dev.zio.client.render;

import dev.zio.client.render.text.CharacterRange;
import dev.zio.client.render.text.ContiguousCharacterRange;
import dev.zio.client.render.text.DeltaEncodedCharacterRange;
import dev.zio.client.render.text.ExplicitCharacterRange;
import dev.zio.client.ZioClient;

public enum FontLanguage {
   SPECIAL(new ExplicitCharacterRange('∞', '♫', '♪')),
   LATIN(new ContiguousCharacterRange(0, 383)),
   CYRILLIC(new ContiguousCharacterRange(1024, 1327)),
   /**
    * Extra glyphs used by the zh_cn translation that the bundled range_cn.bin omitted.
    *
    * <p>The atlas only renders characters present in the collected ranges, so a missing
    * character is silently dropped; "杀戮光环" used to render as "杀光环". Adding these
    * here keeps the binary range file untouched while letting the labels display.
    */
   CHINESE_EXTRA(new ExplicitCharacterRange('戮', '氛', '昵', '隧', '骼', '。', '剔', '棺', '迭', '遁', '阈')),
   CHINESE(new DeltaEncodedCharacterRange(19968, ZioClient.class.getResourceAsStream("/assets/zio/font/range_cn.bin"))),
   JAPANESE(
      new DeltaEncodedCharacterRange(19968, ZioClient.class.getResourceAsStream("/assets/zio/font/range_jp.bin"))
   ),
   JAPANESE_ALPHABET(new ContiguousCharacterRange(12288, 12543));

   private final CharacterRange characterRange;

   FontLanguage(CharacterRange characterRange) {
      this.characterRange = characterRange;
   }

   public CharacterRange getRange() {
      return this.characterRange;
   }

}
