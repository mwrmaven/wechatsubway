package com.maven.dealwith.wechat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

/**
 * 解析网页中需要查询的数据
 * create by maven 2018/12/14
 */
@Service
public class ParseStationHtml {
    private static final String[] times = new String[]{"首车时间", "末车时间"};
    private static final String twoTime = ",1号线首末车时刻表,5号线首末车时刻表,7号线首末车时刻表,8号线首末车时刻表,9号线首末车时刻表,14号线东段（含中段）首末车时刻表,16号线首末车时刻表,八通线首末车时刻表,亦庄线首末车时刻表,房山线首末车时刻表";
    private static final String threeTime = ",2号线首末车时刻表,,,,,,,,,,,,,,,,,,,,,,,,,,";
    private static final String likeFourThread = ",4号线/大兴线首末车时刻表,,,,,,,,,,,,,,,,,,,,,,,,,,,";
    private static final String likeFourTeenThread = ",14号线（西段）首末车时刻表";
    private static final String likeTenThread = ",10号线首末车时刻表";
    private static final String likeThirTeenThread = ",13号线首末车时刻表";
    private static final String likeFifTeenThread = ",15号线首末车时刻表";
    private static final String likeSixThread = ",6号线首末车时刻表";
    private static final String likeChampionThread = ",昌平线首末车时刻表";
    private static final String flag = "*****************************";

    /**
     * 解析bj地铁网页
     * @param stationHtml
     * @param stationName
     * @return
     */
    public String parse(String stationHtml, String stationName) {
        // 处理网页中的 th 标签
        stationHtml = stationHtml.replace("<th>", "<td>").replace("<th ", "<td ").replace("</th>", "</td>");
        Document doc = Jsoup.parse(stationHtml);

        // 获取包含地铁名称的table，遍历之后解析
        Elements tables = doc.select("table:contains(" + stationName + ")");
        StringBuilder result = new StringBuilder();
        for (Element table : tables) {
            String tmp = getFromBJTable(table, stationName);
            if (!result.toString().contains(tmp)) {
                result.append(tmp).append("\n\n");
            }
        }
        return result.toString();
    }

    /**
     * 解析table，判断地铁站，返回格式化后的数据
     * @param table
     * @param stationName
     * @return
     */
    private String getFromBJTable(Element table, String stationName) {
        // 获取地铁线路
        Element thead = table.selectFirst("thead");
        String thread = "," + table.selectFirst("tr").text();
        String title = thread.replace(",", "");
        StringBuilder sb = new StringBuilder(title.substring(0, (title.length() - 6))).append("\n")
                .append(flag).append("\n");

        if (twoTime.contains(thread)) {
            // 获取开往方向
            Elements theadTds = thead.select("tr:contains(车站名称) td");
            String[] directions = new String[]{theadTds.get(1).text(), theadTds.get(2).text()};

            // 获取包含地铁名的tr
            Elements trs = table.select("tr:contains(" + stationName + ")");
            for (Element tr : trs) {
                Elements tds = tr.select("td");
                if (!stationName.equals(tds.first().text().replaceAll(" ", ""))) {
                    continue;
                }

                sb.append(times[0]).append("\n");
                for (int i = 1; i < tds.size(); i++) {
                    sb.append(stationName).append("——").append(directions[(i - 1) / 2]).append(" ").append(tds.get(i).text()).append("\n");
                }
                sb.append(flag).append("\n");
                sb.append(times[1]).append("\n");
                for (int j = 2; j < tds.size(); j += 2) {
                    sb.append(stationName).append("——").append(directions[(j - 1) / 2]).append(" ").append(tds.get(j).text()).append("\n");
                }
            }
        } if (threeTime.contains(thread)) {
            //获取开往方向
            Elements theadTds = thead.select("tr:contains(车站名称) td");
            Elements endTds = thead.select("tr:contains(全程) td");
            String[] directions = new String[]{theadTds.get(1).text(), endTds.get(1).text(), theadTds.get(2).text(), endTds.get(3).text()};
            //{外环（西直门-复兴门-东直门-西直门）, 终点积水潭, 内环（积水潭-东直门-复兴门-积水潭）, 终点西直门}

            //获取包含地铁名的tr，遍历这些tr
            Elements trs = table.select("tr:contains(" + stationName + ")");
            for (int number = 0; number < trs.size(); number++) {
                Elements tds = trs.get(number).select("td");
                if (!stationName.equals(tds.first().text().replaceAll(" ", ""))) {
                    continue;
                }

                sb.append(times[0]).append("\n");
                //解析tr中的tds
                for (int i = 1; i < tds.size(); i += 3) {
                    sb.append(stationName).append("——").append(directions[i / 2]).append(" ").append(tds.get(i).text()).append("\n");
                }
                sb.append(flag).append("\n");
                sb.append(times[1]).append("\n");
                for (int j = 2; j < 4; j++) {
                    if (j == 2) {
                        sb.append(stationName).append("——").append(directions[0]).append(" ").append(tds.get(j).text()).append("\n");
                    } else {
                        sb.append(stationName).append("——").append(directions[j / 2]).append(" ").append(tds.get(j).text()).append("\n");
                    }
                    sb.append(stationName).append("——").append(directions[(j + 3) / 2]).append(" ").append(tds.get(j + 3).text()).append("\n");
                }
            }
        } else if (likeFourThread.contains(thread)) {
            //获取开往方向
            Elements theadTds = thead.select("tr").get(2).select("td");
            String[] directions = new String[]{"上行", "下行", theadTds.first().text(), theadTds.get(1).text()};
            //{"上行", "下行", 往天宫院, 往公益西桥}

            //获取包含地铁名的tr，遍历这些tr
            Elements trs = table.select("tr:contains(" + stationName + ")");
            StringBuffer begin = new StringBuffer(times[0]).append("\n");
            StringBuffer end = new StringBuffer(times[1]).append("\n");
            for (int number = 0; number < trs.size(); number++) {
                String text = trs.get(number).select("td:contains(" + stationName + ")").text().replaceAll(" ", "");
                if (!stationName.equals(text)) {
                    continue;
                }

                Elements tds = trs.get(number).select("td");
                if (stationName.equals(tds.first().text())) {
                    begin.append(stationName).append("——").append(directions[0]).append(" ").append(tds.get(1).text()).append("\n");
                    end.append(stationName).append("——").append(directions[0]).append(" ").append(tds.get(2).text()).append("\n");
                } else {
                    begin.append(stationName).append("——").append(directions[1]).append(" ").append(tds.get(4).text()).append("\n");
                    end.append(stationName).append("——").append(directions[2]).append(" ").append(tds.get(5).text()).append("\n");
                    end.append(stationName).append("——").append(directions[3]).append(" ").append(tds.get(6).text()).append("\n");
                }
            }
            sb.append(begin).append(flag).append("\n").append(end);
        } else if (likeSixThread.contains(thread)) {
            //获取开往方向
            Elements theadTds = thead.select("tr:contains(车站名称) td");
            Elements endTds = thead.select("tr:contains(全程) td");
            String[] directions = new String[]{theadTds.get(1).text(), theadTds.get(2).text(), endTds.last().html().split("<br>")[1]};

            //获取包含地铁名的tr，遍历这些tr
            Elements trs = table.select("tr:contains(" + stationName + ")");
            for (int number = 0; number < trs.size(); number++) {
                Elements tds = trs.get(number).select("td");
                if (!stationName.equals(tds.first().text().replaceAll(" ", ""))) {
                    continue;
                }

                sb.append(times[0]).append("\n");
                //解析tr中的tds
                for (int i = 1; i < 4; i += 2) {
                    sb.append(stationName).append("——").append(directions[(i - 1) / 2]).append(" ").append(tds.get(i).text()).append("\n");
                }
                sb.append(flag).append("\n");
                sb.append(times[1]).append("\n");
                for (int j = 2; j < tds.size(); j++) {
                    if (j == 3) {
                        continue;
                    }
                    sb.append(stationName).append("——").append(directions[j / 2]).append(" ").append(tds.get(j).text()).append("\n");
                }

            }
        } else if (likeThirTeenThread.contains(thread)) {
            String[] directions = new String[]{"往西直门", "往东直门", "往霍营站", "往回龙观站"};
            //获取包含地铁名的tr，遍历这些tr
            Elements trs = table.select("tr:contains(" + stationName + ")");

            for (int number = 0; number < trs.size(); number++) {
                Elements tds = trs.get(number).select("td");
                if (!stationName.equals(tds.first().text().replaceAll(" ", ""))) {
                    continue;
                }

                sb.append(times[0]).append("\n");
                //解析tr中的tds
                for (int i = 1; i < 3; i++) {
                    sb.append(stationName).append("——").append(directions[i - 1]).append(" ").append(tds.get(i).text()).append("\n");
                }
                sb.append(flag).append("\n");
                sb.append(times[1]).append("\n");
                for (int j = 3; j < tds.size(); j++) {
                    sb.append(stationName).append("——").append(directions[j - 3]).append(" ").append(tds.get(j).text()).append("\n");
                }

            }
        } else if (likeTenThread.contains(thread)) {
            String[] directions = new String[]{"下行（内环）巴沟-国贸-宋家庄-车道沟方向", "终点站巴沟", "终点站成寿寺", "上行（外环）车道沟-宋家庄-国贸-巴沟方向", "终点站车道沟"};
            //获取包含地铁名的tr，遍历这些tr
            Elements trs = table.select("tr:contains(" + stationName + ")");
            StringBuffer begin = new StringBuffer(times[0]).append("\n");
            StringBuffer end = new StringBuffer(times[1]).append("\n");
            for (int number = 0; number < trs.size(); number++) {
                String text = trs.get(number).select("td:contains(" + stationName + ")").text().replaceAll(" ", "");
                if (!stationName.equals(text)) {
                    continue;
                }

                Elements tds = trs.get(number).select("td");
                if (stationName.equals(tds.first().text())) {
                    begin.append(stationName).append("——").append(directions[0]).append(" ").append(tds.get(1).text()).append("\n");
                    end.append(stationName).append("——").append(directions[0]).append(" ").append(tds.get(2).text()).append("\n");
                    end.append(stationName).append("——").append(directions[1]).append(" ").append(tds.get(3).text()).append("\n");
                    end.append(stationName).append("——").append(directions[2]).append(" ").append(tds.get(4).text()).append("\n");
                } else {
                    begin.append(stationName).append("——").append(directions[3]).append(" ").append(tds.get(6).text()).append("\n");
                    end.append(stationName).append("——").append(directions[3]).append(" ").append(tds.get(7).text()).append("\n");
                    end.append(stationName).append("——").append(directions[4]).append(" ").append(tds.get(8).text()).append("\n");
                }
            }
            sb.append(begin).append(flag).append("\n").append(end);
        } else if (likeChampionThread.contains(thread)) {
            String[] directions = new String[]{"往西二旗方向", "终点站朱辛庄", "往昌平西山口"};
            //获取包含地铁名的tr，遍历这些tr
            Elements trs = table.select("tr:contains(" + stationName + ")");

            for (int number = 0; number < trs.size(); number++) {
                Elements tds = trs.get(number).select("td");
                if (!stationName.equals(tds.first().text().replaceAll(" ", ""))) {
                    continue;
                }

                sb.append(times[0]).append("\n");
                //解析tr中的tds
                sb.append(stationName).append("——").append(directions[0]).append(" ").append(tds.get(1).text()).append("\n");
                sb.append(stationName).append("——").append(directions[2]).append(" ").append(tds.get(2).text()).append("\n");
                sb.append(flag).append("\n");
                sb.append(times[1]).append("\n");
                for (int j = 3; j < tds.size(); j++) {
                    sb.append(stationName).append("——").append(directions[j - 3]).append(" ").append(tds.get(j).text()).append("\n");
                }
            }
        } else if (likeFourTeenThread.contains(thread)) {
            String[] directions = new String[]{"开往西局方向", "开往张郭庄方向", "半程"};
            //获取包含地铁名的tr，遍历这些tr
            Elements trs = table.select("tr:contains(" + stationName + ")");

            for (int number = 0; number < trs.size(); number++) {
                Elements tds = trs.get(number).select("td");
                if (!stationName.equals(tds.first().text().replaceAll(" ", ""))) {
                    continue;
                }

                sb.append(times[0]).append("\n");
                //解析tr中的tds
                for (int i = 1; i < 4; i += 2) {
                    sb.append(stationName).append("——").append(directions[(i - 1) / 2]).append(" ").append(tds.get(i).text()).append("\n");
                }
                sb.append(flag).append("\n");
                sb.append(times[1]).append("\n");
                for (int j = 2; j < tds.size(); j++) {
                    if (j == 3) {
                        continue;
                    }
                    sb.append(stationName).append("——").append(directions[(j - 1) / 2]).append(" ").append(tds.get(j).text()).append("\n");
                }
            }
        } else if (likeFifTeenThread.contains(thread)) {
            String[] directions = new String[]{"开往清华东路西口方向", "半程（终点马泉营）", "开往俸伯方向"};
            //获取包含地铁名的tr，遍历这些tr
            Elements trs = table.select("tr:contains(" + stationName + ")");

            for (int number = 0; number < trs.size(); number++) {
                Elements tds = trs.get(number).select("td");
                if (!stationName.equals(tds.first().text().replaceAll(" ", ""))) {
                    continue;
                }

                sb.append(times[0]).append("\n");
                //解析tr中的tds
                for (int i = 1; i < tds.size(); i += 3) {
                    sb.append(stationName).append("——").append(directions[i / 2]).append(" ").append(tds.get(i).text()).append("\n");
                }
                sb.append(flag).append("\n");
                sb.append(times[1]).append("\n");
                for (int j = 2; j < tds.size(); j++) {
                    if (j == 4) {
                        continue;
                    }
                    sb.append(stationName).append("——").append(directions[(j - 1) / 2]).append(" ").append(tds.get(j).text()).append("\n");
                }
            }
        }

        sb.append(flag).append("\n");
        String result = sb.toString();
        String param = new StringBuffer(title.substring(0, (title.length() - 6))).append("\n").append(flag).append("\n").append(flag).append("\n").toString();
        if (result.equals(param)) {
            return "";
        } else {
            return result;
        }
    }

//    public static void main(String[] args) {
//        String url = "http://www.bjsubway.com/e/action/ListInfo/?classid=39&ph=1";
//        String subwayHtml = new Subway().getHtml(url).replaceAll("<th>", "<td>");
//        System.out.println(subwayHtml);
//        subwayHtml = subwayHtml.replaceAll("<th ", "<td ");
//        subwayHtml = subwayHtml.replaceAll("</th>", "</td>");
//        String results = new ParseStationHtml().parse(subwayHtml, "古城");
//        System.out.println(results);
//    }
}
