import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.testing.TestWordSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;


import java.util.Map;

/**
 * Created by harsha on 10/19/16.
 */
public class RTBDATopologyStorm {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("wordSpout", new MsgCollectSpout(), 10);
        builder.setBolt("exclaim1", new MetadataBolt(), 3).shuffleGrouping("word");
        builder.setBolt("exclaim2", new MetadataBolt(), 2).shuffleGrouping("exclaim1");

        Config conf = new Config();
        conf.setDebug(true);

        if (args != null && args.length > 0) {
            conf.setNumWorkers(3);

            StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
        }
        else {

            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("Lab8Cluster", conf, builder.createTopology());
            Utils.sleep(10000);
            cluster.killTopology("Lab8Cluster");
            cluster.shutdown();
        }
    }

}
