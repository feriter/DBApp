package ru.nsu.ccfit.queryForms;

import java.util.ArrayList;

public class ParameterizedQueries {
    public static ArrayList<String> getQuery(int n) {
        var q = new ArrayList<String>();
        switch (n) {
            case 1:
                q.add("SELECT id, model, number\n" +
                        "FROM vehicles;");
                break;
            case 2:
                q.add("SELECT id, name, passport\n" +
                        "FROM persons\n" +
                        "WHERE vehicle_id = ");
                q.add(";");
                break;
            case 3:
                q.add("SELECT name, model, number\n" +
                        "FROM persons\n" +
                        "         INNER JOIN vehicles v on persons.vehicle_id = v.id;");
                break;
            case 4:
                q.add("SELECT vehicles.id, model, number, length\n" +
                        "FROM vehicles\n" +
                        "         INNER JOIN routes r on r.id = vehicles.route_id\n" +
                        "WHERE (vehicles.vehicle_category = 1)\n" +
                        "   OR (vehicles.vehicle_category = 2)\n" +
                        "   OR (vehicles.vehicle_category = 3);");
                break;
            case 5:
                q.add("SELECT date, length, number\n" +
                        "FROM rides\n" +
                        "         INNER JOIN (SELECT vehicles.id as vid, number\n" +
                        "                     FROM vehicles\n" +
                        "                              INNER JOIN vehicle_categories on vehicles.vehicle_category = vehicle_categories.id\n" +
                        "                     WHERE category_name = '");
                q.add("') as cat_vehs on vid = vehicle_id\n" +
                        "WHERE date BETWEEN '");
                q.add("' AND '");
                q.add("';");
                break;
            case 6:
                q.add("SELECT v.id, model, number, date, cost\n" +
                        "FROM repairs\n" +
                        "         INNER JOIN vehicles v on v.id = repairs.vehicle_id\n" +
                        "WHERE model = '");
                q.add("'\n" +
                        "  AND date BETWEEN '");
                q.add("' AND '");
                q.add("';");
                break;
            case 7:
                q.add("SELECT workers_heads_masters.name, head_name, master, fid\n" +
                        "FROM (SELECT name, f.id as faid\n" +
                        "      FROM persons\n" +
                        "               INNER JOIN facilities f on persons.id = f.head_id) persons_facilities\n" +
                        "         INNER JOIN\n" +
                        "     (SELECT heads_workers.name, head_name, pp.name as master, fid\n" +
                        "      FROM (SELECT name, head_name, mid, fid\n" +
                        "            FROM persons\n" +
                        "                     INNER JOIN (SELECT brigades.id as bid, p.name as head_name, master_id as mid, facility_id as fid\n" +
                        "                                 FROM brigades\n" +
                        "                                          INNER JOIN persons p on brigades.head_id = p.id) heads\n" +
                        "                                on heads.bid = persons.brigade_id) heads_workers\n" +
                        "               INNER JOIN persons pp on heads_workers.mid = pp.id) workers_heads_masters on faid = fid;");
                break;
            case 8:
                q.add("SELECT facility_id, count(v.number)\n" +
                        "FROM vehicle_categories\n" +
                        "         INNER JOIN vehicles v on vehicle_categories.id = v.vehicle_category\n" +
                        "GROUP BY facility_id;");
                break;
            case 9:
                q.add("SELECT facility_id, number, model, route_id\n" +
                        "FROM facilities\n" +
                        "         INNER JOIN vehicles v on facilities.id = v.facility_id;");
                break;
            case 10:
                q.add("SELECT model, length, date\n" +
                        "FROM vehicles\n" +
                        "         INNER JOIN rides r on vehicles.id = r.vehicle_id\n" +
                        "WHERE number = '");
                q.add("'\n" +
                        "  AND date BETWEEN '");
                q.add("' AND '");
                q.add("';");
                break;
            case 11:
                q.add("SELECT part_name, part_cost, date\n" +
                        "FROM (\n" +
                        "         SELECT repairs.id as rid, date\n" +
                        "         FROM repairs\n" +
                        "                  INNER JOIN (\n" +
                        "             SELECT vehicles.id as vid\n" +
                        "             FROM vehicles\n" +
                        "                      INNER JOIN vehicle_categories vc on vc.id = vehicles.vehicle_category\n" +
                        "             WHERE category_name = '");
                q.add("') cat_vehs on repairs.vehicle_id = vid) cat_veh_repairs\n" +
                        "         INNER JOIN vehicle_parts on rid = vehicle_parts.used_in\n" +
                        "WHERE date BETWEEN '");
                q.add("' AND '");
                q.add("';");
                break;
            case 12:
                q.add("SELECT v.id, commissioned_at, decommissioned_at\n" +
                        "FROM records\n" +
                        "         INNER JOIN vehicles v on v.id = records.vehicle_id;");
                break;
            case 13:
                q.add("SELECT name, profession_name\n" +
                        "FROM persons\n" +
                        "         INNER JOIN brigades b on b.id = persons.brigade_id\n" +
                        "         INNER JOIN professions p on p.id = persons.profession_id\n" +
                        "WHERE head_id = ");
                q.add(";");
                break;
            case 14:
                q.add("SELECT pname, brigade_id, vehicle_id, date, cost\n" +
                        "FROM (SELECT pp.name as pname, w.repair_id as wrid\n" +
                        "      FROM (SELECT p.id as id, p.name as name\n" +
                        "            FROM professions\n" +
                        "                     INNER JOIN persons p on professions.id = p.profession_id\n" +
                        "            WHERE profession_name = '");
                q.add("') as pp\n" +
                        "               INNER JOIN works w on pp.id = w.person_id) as prof_works\n" +
                        "         INNER JOIN repairs on prof_works.wrid = id\n" +
                        "WHERE date BETWEEN '");
                q.add("' AND '");
                q.add("'\n" +
                        "  AND repairs.vehicle_id = ");
                q.add(";");
                break;
            default:
                q.add("SELECT * FROM persons;");
                break;
        }
        return q;
    }

    public static ArrayList<String> getParameterNames(int n) {
        var p = new ArrayList<String>();
        switch (n) {
            case 2:
                p.add("Vehicle id");
                break;
            case 5:
            case 11:
                p.add("Category");
                p.add("From");
                p.add("Till");
                break;
            case 6:
                p.add("Model");
                p.add("From");
                p.add("Till");
                break;
            case 10:
                p.add("Number");
                p.add("From");
                p.add("Till");
                break;
            case 13:
                p.add("Head id");
                break;
            case 14:
                p.add("Profession");
                p.add("From");
                p.add("Till");
                p.add("Vehicle id");
                break;
            default:
                break;
        }
        return p;
    }
}
