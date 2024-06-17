/**
 * spring Bean에서 해당 Proxy를 생성하여 사용함
 * 프록시 이름은 UserServiceProxy 가 아닌 UserService@CGLIB$214 이런 식으로 생성됨
 */
public class UserServiceProxy {

    private final UserService delegate;
    private final TransactionManager transactionManager;

    public UserServiceProxy(UserService delegate, PlatformTransactionManager transactionManager) {
        this.delegate = delegate;
        this.transactionManager = transactionManager;
    }

    // 외부에서 해당 메서드를 실행함.
    public void saveUser(User user) {
        delegate.saveUser(user);
    }

    // 이 메서드를 호출하는 곳은 없음.
    private void privateSaveUser(User user) {
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            delegate.privateSaveUser(user);
            transactionManager.commit(transaction);
        } catch (Exception e) {
            transactionManager.rollback(transaction);
            throw e;
        }
    }
}
