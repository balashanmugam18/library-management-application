package com.practice.librarymanagement.service;
import com.practice.librarymanagement.repository.BookRepository;
import com.practice.librarymanagement.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

import com.practice.librarymanagement.repository.TransferBookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.practice.librarymanagement.entity.Book;
import com.practice.librarymanagement.entity.Member;
import com.practice.librarymanagement.entity.TransferBook;
import com.practice.librarymanagement.exception.CustomException;
import com.practice.librarymanagement.model.BookRequest;

@Service
public class LibService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final TransferBookRepository transferBookRepository;
    public LibService(BookRepository bookRepository, MemberRepository memberRepository, TransferBookRepository transferBookRepository){
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.transferBookRepository = transferBookRepository;
    }

    public List<Book> getByAuthor(String name){
        List<Book> books = bookRepository.findByAuthor(name);
        if (CollectionUtils.isEmpty(books)) {
            throw new CustomException("No Books found written by "+name);
        }
        return books; 
    }

    public Book addBook(BookRequest bookRequest) {
        Book book = new Book();
        book.setAuthor(bookRequest.getAuthor());
        book.setAvailableCopies(bookRequest.getAvailableCopies());
        book.setCategory(bookRequest.getCategory());
        book.setBookTitle(bookRequest.getTitle());
        book.setIsbn(bookRequest.getIsbn());
        bookRepository.save(book);
        return book;
    }

    @Transactional
    public TransferBook transfer(String memberId, String bookTitle) {


        Optional<Member> member = memberRepository.findByMemberId(memberId);
        Optional<Book> book = bookRepository.findByBookTitle(bookTitle);
        Optional<TransferBook> existingTransfer = transferBookRepository.findByMember(member.get());
        if(!member.isPresent()){
            throw new CustomException("Invalid MembershipId");
        }
        if(!member.get().isActiveStatus()==true){
            throw new CustomException("Inactive MembershipId");
        }

        if(!book.isPresent()){
            throw new CustomException("Book is not available");
        }
        if(book.get().getAvailableCopies()<2){
            throw new CustomException("Currently Stock not available");
        }

        TransferBook transferDetails = new TransferBook();
        transferDetails.setBook(book.get());
        transferDetails.setMember(member.get());
        transferDetails.setHoldings(1);
        transferDetails.setIsbn(book.get().getIsbn());

        int newOverDew = existingTransfer.map(TransferBook::getOverDew).orElse(0) + 1;
        transferDetails.setOverDew(newOverDew);
        transferDetails.setPendingOverDew(true);

        book.get().setAvailableCopies(book.get().getAvailableCopies()-1);
        bookRepository.save(book.get());
        transferBookRepository.save(transferDetails);
        return transferDetails;
    }

}
