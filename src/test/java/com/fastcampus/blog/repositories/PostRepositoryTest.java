package com.fastcampus.blog.repositories;

import com.fastcampus.blog.entities.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    // template: method_scenario_expectedResult

    // check return of deleted and not deleted post
    @Test
    void findByIsDeleted_givenTwoPostsAndOneDeleted_shouldReturnOne(){
        Post post1 = new Post();
        post1.setDeleted(true);

        Post post2 = new Post();
        post2.setDeleted(false);

        postRepository.save(post1);
        postRepository.save(post2);

        List<Post> posts = postRepository.findByIsDeleted(false);
        Assertions.assertNotNull(posts);
        Assertions.assertEquals(1, posts.size());
    }

    // check return of empty list when no posts
    @Test
    void findByIsDeleted_givenNoPosts_shouldReturnEmpty(){
        List<Post> posts = postRepository.findByIsDeleted(false);
        Assertions.assertNotNull(posts);
        Assertions.assertEquals(0, posts.size());
    }

    // check find post by slud and not deleted
    @Test
    void findPostBySlugAndIsDeleted_givenPostWithSlugAndNotDeleted_shouldReturnPost() {
        Post post = new Post();
        post.setSlug("unique-test-slug");
        post.setDeleted(false);
        postRepository.save(post);

        Optional<Post> found = postRepository.findPostBySlugAndIsDeleted("unique-test-slug", false);
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals("unique-test-slug", found.get().getSlug());
    }

    // check find post by id and deleted
    @Test
    void findPostByIdAndIsDeleted_givenPostWithIdAndDeleted_shouldReturnPost() {
        Post post = new Post();
        post.setDeleted(true);
        postRepository.save(post);

        Optional<Post> found = postRepository.findPostByIdAndIsDeleted(post.getId(), true);
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(post.getId(), found.get().getId());
    }

    // check find post by id and not deleted
    @Test
    void findAllByIsDeleted_givenTwoNotDeletedPostsAndPageRequest_shouldReturnPagedPosts() {
        Post post1 = new Post();
        post1.setDeleted(false);
        post1.setSlug("slug-1");

        Post post2 = new Post();
        post2.setDeleted(false);
        post2.setSlug("slug-2");

        postRepository.save(post1);
        postRepository.save(post2);

        Page<Post> page = postRepository.findAllByIsDeleted(false, PageRequest.of(0, 10));
        Assertions.assertNotNull(page);
        Assertions.assertEquals(2, page.getTotalElements());
    }
}