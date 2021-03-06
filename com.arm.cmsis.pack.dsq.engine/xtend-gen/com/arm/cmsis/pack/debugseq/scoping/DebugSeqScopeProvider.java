/**
 * generated by Xtext 2.10.0
 */
package com.arm.cmsis.pack.debugseq.scoping;

import com.arm.cmsis.pack.debugseq.debugSeq.Block;
import com.arm.cmsis.pack.debugseq.debugSeq.CodeBlock;
import com.arm.cmsis.pack.debugseq.debugSeq.Control;
import com.arm.cmsis.pack.debugseq.debugSeq.DebugSeqModel;
import com.arm.cmsis.pack.debugseq.debugSeq.DebugSeqPackage;
import com.arm.cmsis.pack.debugseq.debugSeq.DebugVars;
import com.arm.cmsis.pack.debugseq.debugSeq.Expression;
import com.arm.cmsis.pack.debugseq.debugSeq.Sequence;
import com.arm.cmsis.pack.debugseq.debugSeq.Statement;
import com.arm.cmsis.pack.debugseq.debugSeq.VariableDeclaration;
import com.arm.cmsis.pack.debugseq.scoping.AbstractDebugSeqScopeProvider;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
@SuppressWarnings("all")
public class DebugSeqScopeProvider extends AbstractDebugSeqScopeProvider {
  @Override
  public IScope getScope(final EObject context, final EReference r) {
    IScope _xifexpression = null;
    if (((context instanceof Expression) && Objects.equal(r, DebugSeqPackage.Literals.VARIABLE_REF__VARIABLE))) {
      EObject _eContainer = context.eContainer();
      _xifexpression = this.symbolsDefinedBefore(_eContainer, context);
    } else {
      _xifexpression = super.getScope(context, r);
    }
    return _xifexpression;
  }
  
  protected IScope _symbolsDefinedBefore(final EObject cont, final EObject o) {
    EObject _eContainer = cont.eContainer();
    EObject _eContainer_1 = o.eContainer();
    return this.symbolsDefinedBefore(_eContainer, _eContainer_1);
  }
  
  protected IScope _symbolsDefinedBefore(final DebugSeqModel dsm, final EObject o) {
    DebugVars _debugvars = dsm.getDebugvars();
    EList<Statement> _statements = _debugvars.getStatements();
    Iterable<VariableDeclaration> _filter = Iterables.<VariableDeclaration>filter(_statements, VariableDeclaration.class);
    return Scopes.scopeFor(_filter);
  }
  
  protected IScope _symbolsDefinedBefore(final Sequence seq, final EObject o) {
    EList<CodeBlock> _codeblocks = seq.getCodeblocks();
    Iterable<Block> _blocksDeclaredBefore = this.blocksDeclaredBefore(_codeblocks, o);
    final Function1<Block, Iterable<VariableDeclaration>> _function = (Block it) -> {
      EList<Statement> _statements = it.getStatements();
      return Iterables.<VariableDeclaration>filter(_statements, VariableDeclaration.class);
    };
    Iterable<Iterable<VariableDeclaration>> _map = IterableExtensions.<Block, Iterable<VariableDeclaration>>map(_blocksDeclaredBefore, _function);
    Iterable<VariableDeclaration> _flatten = Iterables.<VariableDeclaration>concat(_map);
    EObject _eContainer = seq.eContainer();
    EObject _eContainer_1 = o.eContainer();
    IScope _symbolsDefinedBefore = this.symbolsDefinedBefore(_eContainer, _eContainer_1);
    return Scopes.scopeFor(_flatten, _symbolsDefinedBefore);
  }
  
  protected IScope _symbolsDefinedBefore(final Block b, final EObject o) {
    EList<Statement> _statements = b.getStatements();
    Iterable<VariableDeclaration> _variablesDeclaredBefore = this.variablesDeclaredBefore(_statements, o);
    EObject _eContainer = b.eContainer();
    EObject _eContainer_1 = o.eContainer();
    IScope _symbolsDefinedBefore = this.symbolsDefinedBefore(_eContainer, _eContainer_1);
    return Scopes.scopeFor(_variablesDeclaredBefore, _symbolsDefinedBefore);
  }
  
  protected IScope _symbolsDefinedBefore(final Control c, final EObject o) {
    EList<CodeBlock> _codeblocks = c.getCodeblocks();
    Iterable<Block> _blocksDeclaredBefore = this.blocksDeclaredBefore(_codeblocks, o);
    final Function1<Block, Iterable<VariableDeclaration>> _function = (Block it) -> {
      EList<Statement> _statements = it.getStatements();
      return Iterables.<VariableDeclaration>filter(_statements, VariableDeclaration.class);
    };
    Iterable<Iterable<VariableDeclaration>> _map = IterableExtensions.<Block, Iterable<VariableDeclaration>>map(_blocksDeclaredBefore, _function);
    Iterable<VariableDeclaration> _flatten = Iterables.<VariableDeclaration>concat(_map);
    EObject _eContainer = c.eContainer();
    EObject _eContainer_1 = o.eContainer();
    IScope _symbolsDefinedBefore = this.symbolsDefinedBefore(_eContainer, _eContainer_1);
    return Scopes.scopeFor(_flatten, _symbolsDefinedBefore);
  }
  
  private Iterable<Block> blocksDeclaredBefore(final List<CodeBlock> list, final EObject o) {
    Iterable<Block> _xifexpression = null;
    if (((o instanceof Block) || (o instanceof Control))) {
      int _indexOf = list.indexOf(o);
      List<CodeBlock> _subList = list.subList(0, _indexOf);
      _xifexpression = Iterables.<Block>filter(_subList, Block.class);
    } else {
      _xifexpression = CollectionLiterals.<Block>newArrayList();
    }
    return _xifexpression;
  }
  
  private Iterable<VariableDeclaration> variablesDeclaredBefore(final List<Statement> list, final EObject o) {
    int _indexOf = list.indexOf(o);
    List<Statement> _subList = list.subList(0, _indexOf);
    return Iterables.<VariableDeclaration>filter(_subList, VariableDeclaration.class);
  }
  
  public IScope symbolsDefinedBefore(final EObject b, final EObject o) {
    if (b instanceof Block) {
      return _symbolsDefinedBefore((Block)b, o);
    } else if (b instanceof Control) {
      return _symbolsDefinedBefore((Control)b, o);
    } else if (b instanceof DebugSeqModel) {
      return _symbolsDefinedBefore((DebugSeqModel)b, o);
    } else if (b instanceof Sequence) {
      return _symbolsDefinedBefore((Sequence)b, o);
    } else if (b != null) {
      return _symbolsDefinedBefore(b, o);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(b, o).toString());
    }
  }
}
