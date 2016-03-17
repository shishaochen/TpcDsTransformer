package shaochen.tpcds.transformer;

/**
 * 提供对store_sales表的数据抽取、转换功能。<br />
 * 输出3或4维的简化表，作为毕设的实验数据。
 * @author Shaochen
 *
 */
public class StoreSalesTransformer extends GeneralTransformer {

	/**
	 * 初始化{@linkplain shaochen.tpcds.transformer.StoreSalesTransformer StoreSalesTransformer}类的新实例。
	 * @param inputPath 数据文件的物理路径。
	 */
	public StoreSalesTransformer(String inputPath) {
		super(inputPath, "utf-8");
	}
	
	/**
	 * 简化数据集以满足Cube操作：<br />
	 * SELECT SUM(ss_quantity) AS quantity FROM store_sales CUBE BY ss_item_sk, ss_customer_sk, ss_store_sk
	 * @param outputPath 输出的物理路径。
	 */
	public void transformTo3D(String outputPath) {
		System.out.println("ss_item_sk|ss_customer_sk|ss_store_sk|ss_quantity|");
		try {
			this.transform(new int[]{2, 3, 7, 10}, outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 简化数据集以满足Cube操作：<br />
	 * SELECT SUM(ss_quantity) AS quantity FROM store_sales CUBE BY ss_sold_date_sk, ss_item_sk, ss_customer_sk, ss_store_sk
	 * @param outputPath 输出的物理路径。
	 */
	public void transformTo4D(String outputPath) {
		System.out.println("ss_sold_date_sk|ss_item_sk|ss_customer_sk|ss_store_sk|ss_quantity|");
		try {
			this.transform(new int[]{0, 2, 3, 7, 10}, outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 简化数据集以满足Cube操作：<br />
	 * SELECT SUM(ss_quantity) AS quantity FROM store_sales CUBE BY ss_sold_date_sk, ss_sold_time_sk, ss_item_sk, ss_customer_sk, ss_store_sk
	 * @param outputPath 输出的物理路径。
	 */
	public void transformTo5D(String outputPath) {
		System.out.println("ss_sold_date_sk|ss_sold_time_sk|ss_item_sk|ss_customer_sk|ss_store_sk|ss_quantity|");
		try {
			this.transform(new int[]{0, 1, 2, 3, 7, 10}, outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 简化数据集以满足Cube操作：<br />
	 * SELECT SUM(ss_quantity) AS quantity FROM store_sales CUBE BY ss_sold_date_sk, ss_sold_time_sk, ss_item_sk, ss_customer_sk, ss_store_sk, ss_promo_sk
	 * @param outputPath 输出的物理路径。
	 */
	public void transformTo6D(String outputPath) {
		System.out.println("ss_sold_date_sk|ss_sold_time_sk|ss_item_sk|ss_customer_sk|ss_store_sk|ss_promo_sk|ss_quantity|");
		try {
			this.transform(new int[]{0, 1, 2, 3, 7, 8, 10}, outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//打印帮助信息
		if (args.length != 6) {
			System.out.println("用法：... -in <数据文件路径> -d <维度数：3-6> -out <输出文件路径>");
			return;
		}
		
		//调度程序执行
		StoreSalesTransformer transformer = new StoreSalesTransformer(args[1]);
		switch (Integer.parseInt(args[3])) {
		case 3:
			transformer.transformTo3D(args[5]); break;
		case 4:
			transformer.transformTo4D(args[5]); break;
		case 5:
			transformer.transformTo5D(args[5]); break;
		case 6:
			transformer.transformTo6D(args[5]); break;
		default:
			throw new IllegalArgumentException("维度数量非有效整数，只可提供3-6的选项！");
		}
	}

}
