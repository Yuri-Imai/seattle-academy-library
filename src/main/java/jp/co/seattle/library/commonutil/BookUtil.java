package jp.co.seattle.library.commonutil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;

@Service
public class BookUtil {
	final static Logger logger = LoggerFactory.getLogger(BookUtil.class);
	private static final String REQUIRED_ERROR = "未入力の必須項目があります";
	private static final String ISBN_ERROR = "ISBNの桁数または半角数字が正しくありません";
	private static final String PUBLISHDATE_ERROR = "出版日は半角数字のYYYYMMDD形式で入力してください";

	/**
	 * 登録前のバリデーションチェック
	 *
	 * @param bookInfo 書籍情報
	 * @return errorList エラーメッセージのリスト
	 */
	public List<String> checkBookInfo(BookDetailsInfo bookInfo) {

		//TODO　各チェックNGの場合はエラーメッセージをリストに追加（タスク４）
		List<String> errorList = new ArrayList<String>();
		// （自分のメモ）ArrayList<String>という箱の中に、errorListを入れている　この後errorListの中身を記述する

		// 必須チェック
		if (!(isEmptyBookInfo(bookInfo))) {
			errorList.add(REQUIRED_ERROR);
		}

		// ISBNのバリデーションチェック
		if (isValidIsbn(bookInfo.getIsbn())) {
			errorList.add(ISBN_ERROR);
		}

		// 出版日の形式チェック
		if (!checkDate(bookInfo.getPublishDate())) {
			errorList.add(PUBLISHDATE_ERROR);
		}

		return errorList;
	}

	/**
	 * 日付の形式が正しいかどうか
	 * 
	 * @param publishDate
	 * @return
	 */
	private static boolean checkDate(String publishDate) {
		try {
			DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			formatter.setLenient(false); // ←これで厳密にチェックしてくれるようになる
			//TODO　取得した日付の形式が正しければtrue（タスク４）
			//if(!publishDate.equals(formatter))
			//🌟publishDateをDate型に変換
			formatter.parse(publishDate);
			//String days = new SimpleDateFormat("yyyyMMdd").format(publishDate);
			String dayyy = formatter.format(formatter.parse(publishDate));

			//（誤入力例：String型）2023/04/07, 2023-04-07, 2023.04.07, にせんにじゅう三年4ガツ四日
			//（Date型）20230407 
			//（String型）20230407

			//（正しい入力例：String型）20230407 
			//（Date型）20230407
			//（String型）20230407

			// 🌟変換したDate型をString型に変換
			//String days = String.valueOf(formatter);

			// 🌟String型に変換したものと引数で渡されたpublishDateと一致しているかをif文で確認
			if (publishDate.equals(dayyy)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception p) {
			p.printStackTrace();
			return false;
		}
	}

	/**
	 * ISBNの形式チェック
	 * 
	 * @param isbn
	 * @return ISBNが半角数字で10文字か13文字かどうか
	 */
	private static boolean isValidIsbn(String isbn) {
		//TODO　ISBNが半角数字で10文字か13文字であればtrue（タスク４)
		if ((isbn.length() == 0) || ((isbn.length() == 10 || isbn.length() == 13) && isbn.matches("^[0-9]+$"))) {
			return false;
			/*} else if ((isbn.length() == 10 || isbn.length() == 13) && isbn.matches("^[0-9]+$")) {
				return false;*/
		} else {
			return true;
		}
	}

	/**
	 * 必須項目の存在チェック
	 * 
	 * @param bookInfo
	 * @return タイトル、著者、出版社、出版日のどれか一つでもなかったらtrue
	 */
	private static boolean isEmptyBookInfo(BookDetailsInfo bookInfo) {
		//TODO　タイトル、著者、出版社、出版日のどれか一つでもなかったらtrue（タスク４）

		//if文　null使う
		String title = bookInfo.getTitle();
		String author = bookInfo.getAuthor();
		String publisher = bookInfo.getPublisher();
		String publishdate = bookInfo.getPublishDate();
		if (title.isEmpty() || author.isEmpty() || publisher.isEmpty() || publishdate.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
}
