package shaochen.tpcds.transformer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * 提供通用的数据文件的抽取、转换操作。
 * @author Shaochen
 *
 */
public class GeneralTransformer {
	
	/**
	 * 数据文件的物理路径。
	 */
	private String inputPath = null;
	
	/**
	 * 数据文件的字符集，如utf-8、gbk等。
	 */
	private String charSet = null;
	
	/**
	 * 初始化{@linkplain shaochen.tpcds.transformer.GeneralTransformer GeneralTransformer}类的新实例。
	 * @param inputPath 数据文件的物理路径。
	 * @param inputCharSet 数据文件的字符集，如utf-8、gbk等。
	 */
	public GeneralTransformer(String inputPath, String charSet) {
		this.inputPath = inputPath;
		this.charSet = charSet;
	}
	
	/**
	 * 执行数据文件的抽取、转换，并输出到指定路径。
	 * @param positions 抽取的列的序号集合。
	 * @param outputPath 转换输出的物理路径。
	 * @throws IOException
	 */
	protected void transform(int[] positions, String outputPath) throws IOException {
		BufferedReader br = null;
		PrintWriter pw = null;
		long rowCount = 0;
		try {
			//创建输入输出流
			br = new BufferedReader(new InputStreamReader(new FileInputStream(this.inputPath), charSet));
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputPath), this.charSet));
			
			//逐行读取数据，执行抽取、转换后输出
			String line = null;
			line = br.readLine();
			while (line != null) {
				String[] fields = line.split("\\|");
				if (fields.length > 0) {
					for (int i = 0; i < positions.length; i++) { //提取指定列，空值设为0
						if (positions[i] < fields.length) {
							String token = fields[positions[i]];
							pw.print(token.isEmpty() ? "0" : token);
						} else {
							pw.print("0");
						}
						pw.print("|");
					}
					pw.println();
				}
				rowCount++;
				line = br.readLine();
			}
		} catch (Exception e) {
			throw new IOException("数据文件格式错误！", e);
		} finally {
			System.out.println(rowCount + " records done");
			
			//关闭输入输出流
			if (pw != null) pw.close();
			if (br != null) {
				try {
					br.close();
				} catch (IOException ioe) {
					throw new IOException("输入流关闭时错误！", ioe);
				}
			}
		}
	}

}
