package lh.h.config;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();

    public String markdown(String markdown) {
        Node document = parser.parse(markdown);
        String html = renderer.render(document);

        // 코드 블록을 <pre> 없이 <code>로 변환
        html = html.replace("<pre><code>", "<code>").replace("</code></pre>", "</code>");

        return html;
    }
}
