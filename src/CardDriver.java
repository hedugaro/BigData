import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.ScrollPaneConstants;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;


import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class CardDriver extends Configured implements Tool 
{
	@Override
	public int run(String[] args) throws Exception 
	{
		
		String input, output;
		if (args.length == 2) 
		{
			input = args[0];
			output = args[1];
		} 
		else 
		{
			System.err.println("Incorrect number of arguments.  Expected: input output");
			return -1;
		}

		Job job = new Job(getConf());
		job.setJarByClass(CardDriver.class);
		job.setJobName(this.getClass().getName());

		FileInputFormat.setInputPaths(job, new Path(input));
		FileOutputFormat.setOutputPath(job, new Path(output));

		job.setMapperClass(CardMapper.class);
		job.setReducerClass(CardTotalReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		job.getConfiguration().set("mapreduce.output.basename", "Customers1.csv");

		boolean success = job.waitForCompletion(true);
		return success ? 0 : 1;
	}

	private JFrame frmBigDataIntegrator;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args)  throws Exception 
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CardDriver window = new CardDriver();
					window.frmBigDataIntegrator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		CardDriver driver = new CardDriver();
		int exitCode = ToolRunner.run(driver, args);
		//System.exit(exitCode);
	}
	
	/**
	 * Create the application.
	 */
	public CardDriver() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBigDataIntegrator = new JFrame();
		frmBigDataIntegrator.setTitle("Big Data Integrator");
		frmBigDataIntegrator.setBounds(100, 100, 600, 609);
		frmBigDataIntegrator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBigDataIntegrator.getContentPane().setLayout(null);
		
		JPanel pInput = new JPanel();
		pInput.setBounds(12, 12, 572, 119);
		frmBigDataIntegrator.getContentPane().add(pInput);
		pInput.setLayout(null);
		
		JLabel lblSelectInputFiles = new JLabel("Select input files:");
		lblSelectInputFiles.setBounds(0, 0, 123, 15);
		pInput.add(lblSelectInputFiles);
		
		JList lInputFiles;// = new JList();
	

		
		JButton bSelect = new JButton("Select");
		bSelect.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				 Path pt=new Path("hdfs://quickstart.cloudera:8020/user/cloudera/test/Customers1.csv");//Location of file in HDFS
			        FileSystem fs;
					try 
					{
						fs = FileSystem.get(new URI("hdfs://quickstart.cloudera:8020"), new Configuration());
					//	fs = FileSystem.get(new Configuration());
						 BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(pt)));
					        String line;
					        line=br.readLine();
					        while (line != null)
					        {
					            System.out.println(line);
					            line=br.readLine();
					}
					}
					        catch (IOException | URISyntaxException e1) 
					        {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						}
					
			}	
		});
		bSelect.setBounds(453, 82, 107, 25);
		pInput.add(bSelect);
		
		JLabel lblFileSelected = new JLabel("1 file selected.");
		lblFileSelected.setBounds(12, 90, 123, 15);
		pInput.add(lblFileSelected);
		
		JPanel pColumns = new JPanel();
		pColumns.setEnabled(false);
		pColumns.setBounds(12, 143, 572, 119);
		frmBigDataIntegrator.getContentPane().add(pColumns);
		pColumns.setLayout(null);
		
		JLabel lblSelectColumns = new JLabel("Select columns:");
		lblSelectColumns.setBounds(0, 0, 156, 15);
		pColumns.add(lblSelectColumns);
		
		JList list = new JList();
		list.setBounds(10, 22, 548, 64);
		pColumns.add(list);
		
		JButton button_1 = new JButton("Select");
		button_1.setBounds(451, 88, 107, 25);
		pColumns.add(button_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 291, 572, 108);
		frmBigDataIntegrator.getContentPane().add(scrollPane);
		
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(200, 200));
		scrollPane.setViewportView(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(39, 97, 61, 15);
		panel_2.add(lblNewLabel);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Join columns?");
		chckbxNewCheckBox.setBounds(8, 8, 118, 23);
		panel_2.add(chckbxNewCheckBox);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(12, 411, 572, 156);
		frmBigDataIntegrator.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblOutput = new JLabel("Output:");
		lblOutput.setBounds(0, 0, 61, 15);
		panel_3.add(lblOutput);
		
		textField = new JTextField();
		textField.setBounds(10, 27, 114, 21);
		panel_3.add(textField);
		textField.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(136, 24, 94, 24);
		panel_3.add(comboBox);
		
		JButton btnNewButton = new JButton("Generate Files");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(423, 119, 137, 25);
		panel_3.add(btnNewButton);
		
		JButton button = new JButton("New button");
		button.setBounds(259, 25, 107, 25);
		panel_3.add(button);
		
		
		  List<String> where = new ArrayList<String>();      
	      //1. Get the Configuration instance
	      Configuration configuration = new Configuration();
	      //2. Get the instance of the HDFS
	      FileSystem hdfs = null;
			try {
				hdfs = FileSystem.get(new URI("hdfs://quickstart.cloudera:8020"), configuration);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      //3. Get the metadata of the desired directory
	      FileStatus[] fileStatus = null;
			try {
				fileStatus = hdfs.listStatus(new Path("hdfs://quickstart.cloudera:8020/user/cloudera/test"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      //4. Using FileUtil, getting the Paths for all the FileStatus
	      Path[] paths = FileUtil.stat2Paths(fileStatus);
	      //5. Iterate through the directory and display the files in it
	      for(Path path : paths)
	      {
	      	   where.add(path.getName().toString());

	      }
	      
	      String listData[] = where.toArray(new String[where.size()]);
	      lInputFiles = new JList( listData );
	  	lInputFiles.setBounds(12, 14, 548, 64);
			pInput.add(lInputFiles);
	}
	

	
}