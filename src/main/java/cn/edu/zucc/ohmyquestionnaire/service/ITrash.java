package cn.edu.zucc.ohmyquestionnaire.service;

import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanTrashQuestionnaire;
import org.springframework.data.domain.Page;

public interface ITrash {
    /**
     * 从垃圾箱获得
     *
     * @param id
     * @return
     */
    BeanTrashQuestionnaire trashQuestionnaire(String id);

    /**
     * 垃圾箱分页查看
     *
     * @param uid
     * @param page
     * @param size
     * @return
     */
    Page<BeanTrashQuestionnaire> trashPageQuestionnaire(int uid, int page, int size);

    /**
     * 删除垃圾箱的一个问卷
     *
     * @param id
     */
    void deleteTrashQuestionnaire(String id);

    /**
     * 移入垃圾箱
     *
     * @param trashQuestionnaire
     * @return
     */
    BeanTrashQuestionnaire addToTrash(BeanTrashQuestionnaire trashQuestionnaire);
}
