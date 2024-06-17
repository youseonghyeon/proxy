
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(User user) {
        this.privateSaveUser(user);
    }

    @Transactional // 해당 어노테이션은 단순 어노테이션으로 실행되지 않음
    private void privateSaveUser(User user) {
        userRepository.save(user);
    }
}
