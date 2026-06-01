package com.librarymanagement.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "BorrowDetails")
@Entity
public class TransferBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    
    @OneToOne
    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
    public Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Integer getHoldings() {
        return holdings;
    }

    public void setHoldings(Integer holdings) {
        this.holdings = holdings;
    }

    public Integer getOverDew() {
        return overDew;
    }

    public void setOverDew(Integer overDew) {
        this.overDew = overDew;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isPendingOverDew() {
        return pendingOverDew;
    }

    public void setPendingOverDew(boolean pendingOverDew) {
        this.pendingOverDew = pendingOverDew;
    }

    public Integer holdings;

    public Integer overDew;

    @ManyToOne
    @JoinColumn(name = "bookTitle", referencedColumnName = "bookTitle")
    public Book book;

    public String isbn;

    public boolean pendingOverDew;

}
