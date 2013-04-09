/**
 * 
 */
package simhash;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;

/**
 * @author zhangcheng
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		if (args.length != 2) {
			System.err.println("Usage: inputfile outputfile");
			return;
		}
		Simhash simHash = new Simhash(new BinaryWordSeg());
		ArrayList<Long> hashes = Lists.newArrayList();
		Map<String, HashSet<Integer>> hashIndex = Maps.newHashMap();
		List<String> lines = Files.readLines(new File(args[0]), Charsets.UTF_8);
		int idx = 0;
		System.out.println("start to build index");
		for(String line : lines) {
			long hash = simHash.simhash64(line);
			System.out.println(line + " " + hash);
			hashes.add(hash);
			StringBuilder bui = new StringBuilder();
			int step = 0;
			for(int i = 0; i < 64; ++i) {
				bui.append((hash >> i) & 1);
				++step;
				if (step % 12 == 0) {
					String key = bui.toString();
					bui = new StringBuilder();
					if (hashIndex.containsKey(key)) {
						hashIndex.get(key).add(idx);
					} else {
						HashSet<Integer> vector = new HashSet<Integer>();
						vector.add(idx);
						hashIndex.put(key, vector);
					}
				}
			}
			++idx;
		}
		System.out.println("build index done");
		File output = new File(args[1]);
		idx = 0;
		int[] bits = new int[lines.size()];
		for(String line : lines) {
			if (bits[idx] == 1) {
				++idx;
				continue;
			}
			long hash = simHash.simhash64(line);
			StringBuilder bui = new StringBuilder();
			int step = 0;
			HashSet<Integer> mayNos = Sets.newHashSet();
			for(int i = 0; i < 64; ++i) {
				bui.append((hash >> i) & 1);
				++step;
				if (step % 12 == 0) {
					String key = bui.toString();
					bui = new StringBuilder();
					if (hashIndex.containsKey(key)) {
						mayNos.addAll(hashIndex.get(key));
					}
				}
			}
			List<Integer> sameNos = Lists.newLinkedList();
			Map<Integer, Integer> dists = Maps.newHashMap();
			for(Integer i : mayNos) {
				int dist = simHash.hammingDistance(hash, hashes.get(i));
				if (dist <= 3) {
					sameNos.add(i);
					bits[i] = 1;
					dists.put(i, dist);
				}
			}
			if (!sameNos.isEmpty()) {
				Files.append("start\n", output, Charsets.UTF_8);
				Files.append(lines.get(idx) + "\n", output, Charsets.UTF_8);
				for(int i : sameNos) {
					if (i == idx) continue;
					Files.append(lines.get(i) + "\t" + dists.get(i) + "\n", output, Charsets.UTF_8);
				}
				Files.append("end\n", output, Charsets.UTF_8);
			}
			bits[idx] = 1;
			++idx;
		}
		
	}

}
