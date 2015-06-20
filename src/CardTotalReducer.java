import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CardTotalReducer extends Reducer<Text, Text, Text, Text> 
{
	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException,
			InterruptedException 
			{
		String a = "";
//		int sum = 0;
		// Go through all values to sum up card values for a card suit
//		for (IntWritable value : values) 
	//	{
	//		sum += value.get();
	//	}
		//context.write(key, new IntWritable());
		context.write(key, null);
	}
}