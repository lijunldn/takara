//package me.takara.client.repository;
//
//import me.takara.client.TakaraRepository;
//import me.takara.shared.TakaraContext;
//
//import java.util.logging.Logger;
//
//public class EquityRepository extends TakaraRepository {
//
//    private static Logger log = Logger.getLogger(EquityRepository.class.getName());
//
//    EquityRepository(TakaraContext context) {
//        super(context);
//        log.info("EquityRepository created");
//    }
//
//    public static EquityRepository of(TakaraRepository repository) {
//        return (EquityRepository)repository;
//    }
//}
