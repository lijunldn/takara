//package me.takara.client.repository;
//
//import me.takara.client.TakaraRepository;
//import me.takara.shared.TakaraContext;
//
//import java.util.logging.Logger;
//
//public class BondRepository extends TakaraRepository {
//
//    private static Logger log = Logger.getLogger(BondRepository.class.getName());
//
//    /**
//     * Constructs a TakaraRepository with <B>TakaraBuilder</B>
//     *
//     * @param context
//     */
//    BondRepository(TakaraContext context) {
//        super(context);
//        log.info("BondRepository created");
//    }
//
//    public static BondRepository of(TakaraRepository repository) {
//        return (BondRepository)repository;
//    }
//
//}
