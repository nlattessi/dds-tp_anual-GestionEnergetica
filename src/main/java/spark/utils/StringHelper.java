package spark.utils;

import java.io.IOException;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

public enum StringHelper implements Helper<String> {

	isDispositivoInteligente {
		@Override
		public CharSequence apply(String arg0, Options arg1) throws IOException {
			if (arg0.equals("dispositivo_inteligente")) {
				return "Si";
			} else {
				return "No";
			}
		}
	}

}