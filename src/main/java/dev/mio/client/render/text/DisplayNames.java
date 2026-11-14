/*
 * ─────────────────────────────────────────────
 * Licensed under the MIT License.
 *
 * Zio is not affiliated with or endorsed by Mio.
 * Provided "as is", without warranty of any kind.
 * ─────────────────────────────────────────────
 */

package dev.zio.client.render.text;

import dev.zio.client.ClientContext;
import dev.zio.client.modules.Module;
import dev.zio.client.modules.ModuleCategory;
import dev.zio.client.settings.Setting;

/**
 * Display-name translation for the ClickGUI.
 *
 * <p>Internal names ({@link dev.zio.client.NamedFeature#getName()}) are load-bearing: they are
 * written to config files, matched by search, and used as command aliases. This class therefore
 * never touches them. It only maps an internal name to a translated label at draw time, falling
 * back to the internal name whenever no translation exists.
 *
 * <p>Translation key shapes, all resolved through {@link LanguageTable}:
 * <ul>
 *   <li>{@code names.categories.<InternalName>} for panel headers</li>
 *   <li>{@code names.modules.<InternalName>} for module rows</li>
 *   <li>{@code names.settings.<InternalName>} for setting rows</li>
 * </ul>
 */
public final class DisplayNames {
   private DisplayNames() {
   }

   public static String category(ModuleCategory category) {
      if (category == null) {
         return "";
      }
      String internalName = category.getName();
      return translate("names.categories." + internalName, internalName);
   }

   public static String module(Module module) {
      if (module == null) {
         return "";
      }
      String internalName = module.getName();
      return translate("names.modules." + internalName, internalName);
   }

   public static String setting(Setting<?> setting) {
      if (setting == null) {
         return "";
      }
      String internalName = setting.getName();
      return translate("names.settings." + internalName, internalName);
   }

   private static String translate(String key, String fallback) {
      LanguageTable table = ClientContext.languageTable;
      if (table == null) {
         return fallback;
      }
      String translated = table.getTranslation(key);
      return translated == null || translated.isBlank() ? fallback : translated;
   }
}
