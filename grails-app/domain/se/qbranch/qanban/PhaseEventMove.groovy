package se.qbranch.qanban

class PhaseEventMove extends Event implements Comparable{
  
    static constraints = {

    }

    static transients = ['phase']
    Phase phase

    Integer newPhaseIndex

    Phase getPhase(){
        if( !phase && domainId ){
            phase = Phase.findByDomainId(domainId)
        }
        return phase
    }

    transient beforeInsert = {
        domainId = phase.domainId
    }

    transient afterInsert = {
        phase.board.phases.remove(phase)
        phase.board.phases.add(newPhaseIndex, phase)
    }

    int compareTo(Object o) {
        if (o instanceof Event) {
            Event event = (Event) o
            final int BEFORE = -1;
            final int EQUAL = 0;
            final int AFTER = 1;

            if(this.dateCreated < event.dateCreated) return AFTER
            if(this.dateCreated > event.dateCreated) return BEFORE

            return EQUAL
        }
    }

    boolean equals(Object o) {
        if(o instanceof Event) {
            Event event = (Event) o
            if(this.id == event.id)
            return true
        }
        return false
    }
}
